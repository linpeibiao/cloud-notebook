package com.xiaohu.cloud_notebook.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohu.cloud_notebook.mapper.NoteBaseMapper;
import com.xiaohu.cloud_notebook.model.domain.BaseUser;
import com.xiaohu.cloud_notebook.model.domain.NoteBase;
import com.xiaohu.cloud_notebook.model.dto.JoinNoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseInfoDto;
import com.xiaohu.cloud_notebook.service.BaseUserService;
import com.xiaohu.cloud_notebook.service.NoteBaseService;
import com.xiaohu.cloud_notebook.service.UserFeignService;
import com.xiaohu.cloud_notebook_common.anotation.AuthCheck;
import com.xiaohu.cloud_notebook_common.constant.UserRole;
import com.xiaohu.cloud_notebook_common.exception.BusinessException;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.ResultCode;
import com.xiaohu.cloud_notebook_common.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author xiaohu
* @description 针对表【t_note_base(知识库表)】的数据库操作Service实现
* @createDate 2022-11-14 23:37:18
*/
@Service
public class NoteBaseServiceImpl extends ServiceImpl<NoteBaseMapper, NoteBase>
    implements NoteBaseService {
    @Autowired
    private NoteBaseMapper noteBaseMapper;
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private UserFeignService userFeignService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuthCheck(mustRole = UserRole.COMMON_USER)
    public Long addNoteBase(NoteBaseDto noteBaseDto) {
        // 判空
        if (noteBaseDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        String noteBaseName = noteBaseDto.getNoteBaseName();
        if (StringUtils.isEmpty(noteBaseName)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "知识库名不能为空");
        }
        int status = noteBaseDto.getStatus();
        // 判断是否加密
        // TODO 添加状态枚举值
        if (status == 2){
            // 密码是否为空
            if (StringUtils.isEmpty(noteBaseDto.getPassword())){
                throw new BusinessException(ResultCode.PARAMS_ERROR, "请设置加密密码或重新设置状态");
            }
        }
        // 将 noteBaseDto 转换成 实体类对象
        NoteBase noteBase = BeanUtil.copyProperties(noteBaseDto, NoteBase.class, "id");
        // 自动设置创建者 id 和创建者昵称，从 UserHolder 中获取
        User user = UserHolder.get();
        String userNackName = user.getNackname();
        Long userId = user.getId();
        noteBase.setUserNackname(userNackName);
        noteBase.setUserId(userId);
        save(noteBase);
        // 关系表添加记录
        baseUserService.addRecord(noteBase.getId(), noteBase.getUserId());
        return noteBase.getId();
    }

    @Override
    @AuthCheck(mustRole = UserRole.COMMON_USER)
    public boolean infoUpdate(NoteBaseInfoDto noteBaseInfoDto) {
        // 判空
        if (noteBaseInfoDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        Long id = noteBaseInfoDto.getId();
        if (id == null || id < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "知识库 id 不合法");
        }
        // 获取当前登录用户，判断修改的知识库是否属于当前登录用户
        User user = UserHolder.get();
        if (!user.getId().equals(id)){
            throw new BusinessException(ResultCode.NO_AUTH, "无权限");
        }

        // 状态修改判断，如果设置为加密，需要设置密码
        int status = noteBaseInfoDto.getStatus();
        if (status == 2){
            // 密码是否为空
            if (StringUtils.isEmpty(noteBaseInfoDto.getPassword())){
                throw new BusinessException(ResultCode.PARAMS_ERROR, "请设置加密密码或重新设置状态");
            }
        }
        // 将 noteBaseDto 转换成 实体类对象
        NoteBase noteBase = BeanUtil.copyProperties(noteBaseInfoDto, NoteBase.class);
        return updateById(noteBase);
    }

    @Override
    @AuthCheck(mustRole = UserRole.COMMON_USER)
    public boolean transferTo(NoteBaseDto noteBaseDto, Long noteBaseId) {
        // 判空
        if (noteBaseDto == null || noteBaseId < 0){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }

        // 判权
        NoteBase noteBase = getById(noteBaseId);
        // 判断知识库是否存在
        if (noteBase == null){
            throw new BusinessException(ResultCode.NOT_FOUND, "该用户不在知识库中");
        }
        // 判断该知识库的创建者是否为当前登录用户
        User user = UserHolder.get();
        if (!noteBase.getUserId().equals(user.getId())){
            throw new BusinessException(ResultCode.NO_AUTH, "无权限，只能创建者可以转让知识库");
        }

        // 查看转让的用户是否在此知识库
        Long transferTo = noteBaseDto.getUserId();
        if (!isJoin(transferTo, noteBaseId)){
            throw new BusinessException(ResultCode.NOT_FOUND, "该用户不在知识库中");
        }

        // 更新
        noteBase.setUserId(transferTo);
        noteBase.setUserNackname(noteBaseDto.getUserNackName());
        return updateById(noteBase);
    }

    @Override
    @AuthCheck(mustRole = UserRole.COMMON_USER)
    public boolean joinNoteBase(JoinNoteBaseDto joinNoteBaseDto) {
        // 判空
        if (joinNoteBaseDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // userId 当前登录用户拿
        long userId = UserHolder.get().getId();
        long noteBaseId = joinNoteBaseDto.getNoteBaseId();
        if (userId < 1 || noteBaseId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "请求参数不合法");
        }

        // 判断知识库是否存在
        NoteBase noteBase = getById(noteBaseId);
        if (noteBase == null){
            throw new BusinessException(ResultCode.NOT_FOUND, "该知识库不存在");
        }
        // 判断知识库状态
        int status = noteBase.getStatus();
        //   私密 无法加入
        if (1 == status){
            throw new BusinessException(ResultCode.NO_AUTH, "私密知识库无法加入");
        }
        //   加密 需要验证密码
        if (2 == status){
            if (!noteBase.getPassword().equals(joinNoteBaseDto.getPassword())){
                throw new BusinessException(ResultCode.NO_AUTH, "密码输入不正确");
            }
        }

        // 判断是否已经加入（不能重复加入）
        if (isJoin(userId, noteBaseId)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "已经加入");
        }

        BaseUser baseUser = new BaseUser();
        baseUser.setNoteBaseId(noteBaseId);
        baseUser.setUserId(userId);
        return baseUserService.save(baseUser);
    }

    @Override
    @AuthCheck(mustRole = UserRole.COMMON_USER)
    public boolean exitNoteBase(Long noteBaseId) {
        if (noteBaseId == null || noteBaseId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // 获取当前登录用户
        User user = UserHolder.get();
        NoteBase noteBase = getById(noteBaseId);
        long loginUserId = user.getId();

        // 判断是否已经加入
        if (!isJoin(loginUserId, noteBaseId)){
            throw new BusinessException(ResultCode.NO_AUTH, "用户未加入此知识库");
        }

        // 判断是否为知识库创建者 不是创建者，直接删除记录即可
        long createUserId = noteBase.getUserId();
        if (loginUserId != createUserId){
            return baseUserService.remove(new QueryWrapper<BaseUser>()
                    .eq("user_id", loginUserId)
                    .eq("note_base_id", noteBaseId));
        }

        // 若是创建者，查询知识库是否只有自己，是删除知识库
        if (getNoteBaseUserCount(noteBaseId) <= 1){
            return baseUserService.remove(new QueryWrapper<BaseUser>()
                    .eq("user_id", loginUserId)
                    .eq("note_base_id", noteBaseId));
        }else{
            // 否转让
            throw new BusinessException(ResultCode.NO_AUTH, "请转让知识库后退出");
        }
    }

    @Override
    public NoteBase getNoteBaseById(Long noteBaseId) {
        if (noteBaseId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        return getById(noteBaseId);
    }

    @Override
    public IPage<NoteBase> getNoteBasePageByUserId(int pageNum, int pageSize, Long userId) {
        if (userId == null || userId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        Page<NoteBase> noteBases = page(new Page<>(pageNum, pageSize),
                new QueryWrapper<NoteBase>().
                        eq("user_id", userId));
        return noteBases;
    }

    @Override
    public IPage<NoteBase> getNoteBasePageByNoteBaseName(int pageNum, int pageSize, String noteBaseName) {
        if (StringUtils.isEmpty(noteBaseName)){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        Page<NoteBase> noteBases = page(new Page<>(pageNum, pageSize),
                new QueryWrapper<NoteBase>().
                        like("note_base_name", noteBaseName));
        return noteBases;
    }

    @Override
    public List<User> getUsersOfNoteBase(Long noteBaseId) {
        if (noteBaseId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // 先从关系表查出用户 id 列表
        List<BaseUser> baseUserList = baseUserService.list(new QueryWrapper<BaseUser>()
                .eq("note_base_id", noteBaseId));
        // 拿到用户id列表
        List<Long> userIdList = baseUserList.stream().map(baseUser -> baseUser.getUserId()).collect(Collectors.toList());
        // 通过列表查询用户列表
        // 使用 openfeign 调用 user 模块的接口
        List<User> userList = userFeignService.getUserByIds(userIdList).getData();
        return userList;
    }

    @Override
    public boolean isNoteBaseExist(Long noteBaseId) {
        return getById(noteBaseId) != null;
    }

    /**
     * 判断用户是否已经加入知识库
     * @param userId
     * @param noteBaseId
     * @return
     */
    private boolean isJoin(Long userId, Long noteBaseId){

        long count = baseUserService.count(new QueryWrapper<BaseUser>()
                .eq("user_id", userId)
                .eq("note_base_id", noteBaseId));
        return count > 0;
    }

    /**
     * 获取知识库已加入用户数
     * @param noteBaseId
     * @return
     */
    private long getNoteBaseUserCount(Long noteBaseId){
        return baseUserService.count(new QueryWrapper<BaseUser>()
                .eq("note_base_id", noteBaseId));
    }
}





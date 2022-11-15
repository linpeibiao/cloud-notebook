package com.xiaohu.cloud_notebook.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohu.cloud_notebook.mapper.NoteBaseMapper;
import com.xiaohu.cloud_notebook.model.domain.NoteBase;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseDto;
import com.xiaohu.cloud_notebook.service.BaseUserService;
import com.xiaohu.cloud_notebook.service.NoteBaseService;
import com.xiaohu.cloud_notebook_common.exception.BusinessException;
import com.xiaohu.cloud_notebook_common.result.ResultCode;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addNoteBase(NoteBaseDto noteBaseDto) {
        // 判空
        if (noteBaseDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        String noteBaseName = noteBaseDto.getNoteBaseName();
        // TODO 自动设置创建者id 和创建者昵称
        if (StringUtils.isEmpty(noteBaseName)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "知识库名不能为空");
        }
        int status = noteBaseDto.getStatus();
        // 判断是否加密
        if (status == 2){
            // 密码是否为空
            if (StringUtils.isEmpty(noteBaseDto.getPassword())){
                throw new BusinessException(ResultCode.PARAMS_ERROR, "请设置加密密码或重新设置状态");
            }
        }
        // 将 noteBaseDto 转换成 实体类对象
        NoteBase noteBase = BeanUtil.copyProperties(noteBaseDto, NoteBase.class, "id");
        save(noteBase);
        // 关系表添加记录
        baseUserService.addRecord(noteBase.getId(), noteBase.getUserId());
        return noteBase.getId();
    }
}





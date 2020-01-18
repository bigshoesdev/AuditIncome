package com.system.software.solutions.sts.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.software.solutions.sts.dao.LCDDao;
import com.system.software.solutions.sts.dao.MainDao;
import com.system.software.solutions.sts.formbean.DailyAuditedFB;
import com.system.software.solutions.sts.formbean.DailyAuditedFB.DailyAudited;
import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.LCD;
import com.system.software.solutions.sts.model.Main;
import com.system.software.solutions.sts.service.ReportsService;

@Service("reportsService")
@Transactional(value = "transactionManagerMSsql")
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private LCDDao lcdDao;

    @Autowired
    private MainDao mainDao;

    @Override
    public DailyAuditedFB fetchDailyAudited() {
        int previousDays = 7;
        CustomEntity<LCD> lcdEntity = lcdDao.fetchDailyAudited(previousDays);
        DailyAuditedFB auditedFB = new DailyAuditedFB();
        List<DailyAudited> list = auditedFB.getLcdList();
        int lcdGrandTotal = 0;

        for (Object obj : lcdEntity.getList()) {
            Object[] o = (Object[]) obj;
            DailyAudited audited = auditedFB.getDailyAuditedNewInstance();
            audited.setCount((Integer) o[0]);
            audited.setDate((Date) o[1]);
            lcdGrandTotal += audited.getCount();
            list.add(audited);
        }
        auditedFB.setLcdGrandTotal(lcdGrandTotal);

        CustomEntity<Main> pcEntity = mainDao.fetchPCAudited(previousDays);
        List<DailyAudited> pclist = auditedFB.getPcList();
        int pcGrandTotal = 0;

        for (Object obj : pcEntity.getList()) {
            Object[] o = (Object[]) obj;
            DailyAudited audited = auditedFB.getDailyAuditedNewInstance();
            audited.setCount((Integer) o[0]);
            audited.setDate((Date) o[1]);
            pcGrandTotal += audited.getCount();
            pclist.add(audited);
        }
        auditedFB.setPcGrandTotal(pcGrandTotal);

        CustomEntity<Main> ltEntity = mainDao.fetchLTAudited(previousDays);
        List<DailyAudited> ltlist = auditedFB.getLtList();
        int ltGrandTotal = 0;

        for (Object obj : ltEntity.getList()) {
            Object[] o = (Object[]) obj;
            DailyAudited audited = auditedFB.getDailyAuditedNewInstance();
            audited.setCount((Integer) o[0]);
            audited.setDate((Date) o[1]);
            ltGrandTotal += audited.getCount();
            ltlist.add(audited);
        }
        auditedFB.setLtGrandTotal(ltGrandTotal);

        return auditedFB;
    }

}

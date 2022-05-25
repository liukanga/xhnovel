package com.ziqing.xhnovel.config;

import com.ziqing.xhnovel.util.SyncMessageJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz定时任务配置类
 * @author 刘梓清
 */

@Configuration
public class QuartzConfig {

    /**
     * 定时任务1：
     * 同步信息Job（任务详情）
     */
    @Bean
    public JobDetail syncUserJobDetail() {
        String jobGroupName = "PJB_JOBGROUP_NAME";
        return JobBuilder.newJob(SyncMessageJob.class)
                .withIdentity("syncMessageJobDetail", jobGroupName)
                .usingJobData("userName", "pan_junbiao的博客") //设置参数（键值对）
                .usingJobData("blogUrl","https://blog.csdn.net/pan_junbiao")
                .usingJobData("blogRemark","您好，欢迎访问 pan_junbiao的博客")
                .storeDurably() //即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }

    /**
     * 定时任务1：
     * 同步信息Job（触发器）
     */
    @Bean
    public Trigger syncUserJobTrigger() {
        //每天8点执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 8 * * ?");

        //创建触发器
        String triggerGroupName = "PJB_TRIGGERGROUP_NAME";
        return TriggerBuilder.newTrigger()
                .forJob(syncUserJobDetail())//关联上述的JobDetail
                .withIdentity("syncMessageJob", triggerGroupName)//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}

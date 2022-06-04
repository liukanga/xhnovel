package com.ziqing.xhnovel.util;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class SyncMessageJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        //获取JobDetail中传递的参数
        String userName = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("userName");
        String blogUrl = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("blogUrl");
        String blogRemark = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("blogRemark");

        //获取当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //打印信息
        System.out.println("用户名称：" + userName);
        System.out.println("博客地址：" + blogUrl);
        System.out.println("博客信息：" + blogRemark);
        System.out.println("当前时间：" + dateFormat.format(date));
        System.out.println("----------------------------------------");
    }


    public static void main(String[] args) {

        String text = "　　“骗子！老子跟你赶了十几天路了，你没有帮我增进一点灵力也就算了！还让老子到处跑腿给你找吃的！” 　　向南赶了十几天的路后，那灵狸终于受不了了，开始咆哮起来！ 　　无视灵狸的撒泼，夜九淡道：“前面我看到有炊烟，走快点，看有没有客栈。” 　　此时已是夕阳西下。 　　再过半个时辰就要天黑了。 　　夜九加快步伐。 　　“走这么快干嘛？你一个人不人鬼不鬼的东西，还怕鬼不成？” 　　灵狸跑几步，停一下，说上一句。 　　它如飞蛾翅膀一样的小眉皱起，蹲在小路上，看着夜九走远了好多，它又跑上去。 　　前面炊烟升起的地方果然是一家客栈。 　　夜九全身上下最值钱的，就是那把万花剑。 　　在客栈里，她摸出之前战甲上的残余铁片一把，放在客栈老板的桌子上。 　　“我住一晚就走，这些够吗。” 　　那老板看都没看她，将铁片收好，“柴房还有空的，热水自便，去吧。” 　　夜九沉默了一瞬，看着那老板将铁片收到布袋里挂起来。 　　装铁片的布袋很鼓，她皱起眉，最近从战场过来的人很多吧？ 　　夜九朝老板所说的柴房走去，正好听到几道低沉的男声。 　　“秦国被灭了你没听到吗？现在江南江北都是大炎的领地，我们是秦人，现在只能往南逃……越往南越好……” 　　“秦国早就投降了，一直是那个外姓王爷在抵御炎国，现在天下都快是炎国的了……” 　　“秦国是真的气数已尽，皇亲国戚能投敌的早投敌了，从头至尾也只有景王一人死撑着……”“景王死了，我跟你们说，这朝廷的消息，都是最晚才让老百姓知道的，我猜景王一个月前就死了，不然秦国不会亡的这么快……” 　　“喂！死女人，你怎么了！一副死了爹娘老子的神情！” 　　刚追上夜九的灵狸嚎叫道。 　　“噗通”一声夜九整个人倒在地面上，那张秀丽的脸直接跌在泥沼之中。 　　灵狸吓了一跳，凑到夜九脸边。 　　女人的脸上全是污泥，整个人趴在地上，半天没有动静。 　　她满脸泥污与泪水。 　　那样的无声又悲壮。 　　十九年从未大哭过。 　　但突闻景王噩耗，她一直紧绷着的那根弦再也绷不住了，断了。 　　十四万人降敌她不恨。 　　困死孤城她不怨。 　　战马自刎她只觉悲凉。 　　当听到师父死的此刻，原谅她再也忍不住大哭起来。 　　“别嚎了，老子耳朵都要炸了！”灵狸大叫起来。 　　这时候客栈牛棚的方向一大捆草垛子往夜九身上砸来。 　　“哭你娘啊！” 　　“别吵了，还他娘的让不让人清净了！”几个秦国的逃兵朝夜九吼道。 　　灵狸龇牙，这几个人的灵识不纯，吃了修不了灵力不说还坏他修为。 　　这时——突然听到客栈外一阵马蹄声。 　　天刚黑，马蹄声太过明显，那几个逃兵吓得从牛棚的草垛里爬起来。 　　毕竟山野之地，牛都少，更何况是马？ 　　“快撤，走后门出去！怕是有官兵过来了！” 　　一溜烟的，那些个赖皮泼猴逃兵全走得没个人影了。 　　* 　　客栈前堂里，客栈老板哆哆嗦嗦地问道，“大、大爷，您……是打尖还是住……店？” 　　来人一身青灰色衣衫，身材魁梧，头戴灰幔斗笠，他将一串铜钱放在桌上，对客栈老板说道，“在这附近找个女人，干净的，找到这些是你的。” 　　男人的声音沙哑、沉魅。 　　“女……女人？”客栈老板惊出一声冷汗。 　　大晚上，要女人，还要干净的。 　　傻子都想得到要干什么。 　　“对，处子。”来人重复道。 　　这时，戴着斗笠的男人突然向客栈后院望去，也不知他是注意到了什么，他陡然运气朝着客栈柴房的方向里奔去。 　　那男人手中的剑一拔，剑光四射，“妖灵出来受死！” 　　灵狸也被这剑气吓了一跳，这不是寻常的剑气，是药道剑气！ 　　此人是修行药道的弟子。 　　灵狸不敢多留了，杀了修道弟子会坏他的修为，杀不了，跑还是跑的了的。 　　它大眼余光瞥了一眼趴在地上的夜九，“嗖”的一声遛了。 　　自身都难保了，当然管不了这女人了。 　　斗笠男人看着灵狸远去，感受到那妖灵妖气强大，他追上去也打不过。 　　他皱眉收了剑，吓退就好。 　　他转身的时候看到了趴在地上的夜九，以为是被那妖灵畜生吸食魂魄的凡人，他本不想多管闲事。 　　却注意到了这个人，极长的发…… 　　这么长的头发，他长这么大年纪都没见到过。 　　这种情况除了修为极高，就是无聊才会留这么长，但也肯定此人不会是这附近的山野村民。 　　夜九从悲痛中回过神来，正要爬起来，就被一只大手提起了衣领。 　　斗笠下是一张阳刚的脸，剑眉星目，倒是生得五官周正。 　　自那夜苏醒以来，夜九是浑身使不上力，她真看不出来她这身体里集聚了什么“灵力”？甚至觉得那群乌鸦所说不过是玩笑话。就连从乾城的火海里爬出来，她都废了不少力气。 　　所以在偶然间遇到灵狸，她才以帮它弄出本体为由，哄骗它助她上路。灵狸相助，那些小妖小鬼也自动遁行，不敢轻易遭惹他们。 　　“这位兄弟，有话好说，不要动手动脚……咳咳咳。”因为身体内气息不稳，她说完一句话后，便咳得很厉害。 　　“没死？”斗笠男人挑眉，还以为此人被那妖灵吸食了精气。 　　男人提着夜九的衣领，耸动了一下手，正要将她放开，却又觉得这人有些怪异。 　　夜九眨了眨眼睛，她竟然能看到，男人手上微弱的灵气。 　　看来是修道之人，难怪那灵狸像老鼠见了猫一样开溜。 　　而且，此人的灵气里还透着一股……药味。 　　男人凝视着夜九满脸泥污的脸，他的喉结动了一下，连声音都有些颤抖，“女人？” －－－－－－题外话－－－－－－ 　　前文伏笔较多，大家耐心看。关于强不强，这个世界，不是力量强不强来定义的，是内心（灵魂）强不强来定义的。收藏最乖！ 　　 　";

        String[] texts = text.split(" ");
        StringBuilder sb = new StringBuilder();
        int len = texts.length/6;
        int i = 1;
        for (String str : texts){
            if (i == len){
                sb.append("\n");
                i = 1;
            }
            sb.append(str);
            i++;
        }

        System.out.println(sb.toString().replace(" ", "/"));


    }
}

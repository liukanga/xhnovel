package com.ziqing.xhnovel.controller;


import com.alibaba.fastjson.JSON;
import com.ziqing.xhnovel.bean.ImageEntity;
import com.ziqing.xhnovel.bean.KafkaMessageEntity;
import com.ziqing.xhnovel.model.Novel;
import com.ziqing.xhnovel.model.Result;
import com.ziqing.xhnovel.model.User;
import com.ziqing.xhnovel.service.ChapterService;
import com.ziqing.xhnovel.service.NovelService;
import com.ziqing.xhnovel.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private NovelService novelService;

    @GetMapping("/")
    public String index(){

        return "redirect:/novel/query/cid/27566/false";
    }


    @PostMapping("/image")
    @ResponseBody
    public Result<ImageEntity> uploadImage(@RequestPart(value = "file", required = false)MultipartFile file, HttpServletRequest request){

        Result<ImageEntity> result = new Result<>();
        try {
            ImageEntity imageEntity = FileUtils.upload(file);
            log.info("图片文件json：" + JSON.toJSONString(imageEntity));

            result.setData(imageEntity);
            result.setSuccess(true);
            result.setCode(200);
            result.setMessage("上传图片成功");

            HttpSession session = request.getSession();
            session.setAttribute("imageUrl", imageEntity.getUrlPath());

        }catch (Exception e){
            log.error("********* 上传文件失败", e);
            result.setSuccess(false);
            result.setCode(400);
            result.setMessage("上传文件失败");
        }


        return result;
    }


    @GetMapping("/loginPage")
    public String login() {
        return "login";
    }

    @GetMapping("/exit")
    public String signOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute("user");
        if (!Objects.isNull(currentUser)){
            session.removeAttribute(currentUser.getId() + "isFirst");
            session.removeAttribute("user");
        }
        return "login";
    }

    @GetMapping("/reg")
    public String register(@RequestParam(value = "page", required = false, defaultValue = "1")int page, Model model) {

        model.addAttribute("page", page);
        return "reg";
    }

    @Autowired
    private ChapterService chapterService;


    @GetMapping("/uiuitest")
    @ResponseBody
    public Result<List<String>> addUserNovelTable(@RequestParam("num")int num,  Model model){

        Result<List<String>> result = new Result<>();

        result.setData(new ArrayList<>());
        if (num != 12){
            return result;
        }
        String text = "天龙界，独立于神域三千大界之外的一个小界，其大小有一个大界的五十分之一，但也足够惊人了。 　　天龙界界王，便是如今上古龙族族长，天龙界底蕴无比深厚，在上古龙族历史上，曾经出现过真正大界的界王，哪怕现在并非上古龙族的巅峰时期，龙族族内也有半步界王的人物。 　　不过需要申明的是，神域的界王，虽然名义上是一界之王，但真正执掌一界却是不可能的，一个界王家族就算再大，也不过占据几百个星球，或是在主大陆占据十亿里方圆的土地，再大也根本占据不了。 　　这些面积，对神域一个大界来说，不过是沧海一粟罢了，在大界的其它地方，还有无数的宗门、家族，包括圣地。 　　这些宗门最多是交好界王家族，根本不会听从界王的调遣。 　　此时，在天龙界的一处秘密空间狭缝中，有一颗不起眼的小行星，行星直径不过两千里而已。 　　除了四神兽氏族的高层之外，没人知道，这个小行星就是神兽秘境的传送阵所在。 　　“还有两个时辰就是秘境开启的时候了，龙一师兄，听闻今年上古凤族出现了一个天才弟子，战胜了同龄的赤战云，在幻神阵中完成了千人斩，并且新秀试炼中，突破火狱第八层，不知道这些传闻是不是夸大了？” 　　在小行星之上，稀稀落落的站着三十多个人，这些人穿着一个款式的青色衣衫，一看就是统一的门派道服，说话的这个青年身材高大，浑身肌肉结实，一双虎目，气势极为威猛。 　　在上古龙族，有一个神命排行榜，根据各自天赋将三十三岁以下神海、命陨期的新秀弟子进行排名，以幻神阵中的杀人数为参考标准，上榜者共有一百零八人。 　　上古凤族崇尚九，九为数之极。而龙族则崇尚八在上古凤族总部有八部天龙，为上古龙族至高长老会。 　　神命榜排名第一的，称号便是龙一，第二便是龙二，第十为龙十，以此类推，一直到龙一百零八。 　　这种称号是荣耀的象征弟子之间彼此称呼也不叫名字，直接叫称号，刚才说话这个高大男子排行十二便称龙十二，而他说话的对象，则是上古龙族的第一弟子——龙一。 　　此人便是麒麟族使者口中所说的上古龙族顶尖天才了。 　　“应该是真的，这个人，将来很可能成为萧道极一流的人物，不次于我们上古龙族的族长。”一个中等身材的青衣男子说道，此人看上去二十七八岁，长相成熟稳重，充满了威严。他便是龙一了。 　　“成为萧道极一流的人物吗？这样的人界王都会关注吧！不过龙一师兄，你在新秀试炼的时候，可是拿到幻神阵八百五十人斩同时在神龙迷宫中闯到第七层，我们的神龙试炼，竞争比古凤试炼更激烈龙一师兄也不差！” 　　龙一在上古龙族威信极高，在年轻弟子心目中，几乎是不可战胜的存在，众人正说着，便看到天空中突然产生水纹一般的涟漪，一艘灵舰冲破了空间的束缚，出现在这一颗小行星的上空下一刻，十几个青年男女从灵舰上飞下来正是上古凤族的弟子。 　　“上古凤族！”龙一抬头一看，目光锁定在了空中一个蓝衣青年的身上，“以修为来看，那人定然就是林铭了！” 　　“下面就是上古龙族龙神宫的弟子，为首那个便是龙一，此人深不可测，你们小心一些！” 　　萧平从灵舰飞下来的时候，看到龙神宫弟子，用真元传音对林铭等人说道。 　　“龙一？” 　　林铭暗暗打量这眼前的青年，却发现对方如同一汪碧湖，表面平静无波，但任凭人怎么看，就是看不见那碧水之下有什么，包括连此人的修为都看不穿！ 　　“看不穿修为，是修炼了隐藏修为的秘法么？” 　　一般三十三岁以下的俊杰，修为最高不过神海中期，甚至有些绝顶天才还有意压制修为，三十三岁依旧神海初期，这样的修为，林铭不可能看不穿。 　　“这人，有些麻烦······”林铭喃喃自语着，自从经历了阳云之后，林铭就对那些自己看不穿修为的人充满了提防之心。 　　“哈哈，龙一兄，好久不见了，你们上古龙族真是人才济济，这次试炼，派出这么强大的阵容，从龙一兄，到龙三十六，全来了吧！” 　　上古龙族定下神命榜之后有一个好处就是资源分配公正，不管什么资源，都从龙一开始往下排，能不能拿到，全看真本事。 　　比如这次神兽秘境试炼，就是前三十六人来。 　　“萧平兄笑了，这一代新秀弟子，你们上古凤族才是真正的大赢!家－了萧兄这样的人才，还有完美古凤血脉的颜月儿师妹，以及战胜了同龄赤战云前辈的林铭师弟……” 　　龙一笑眯眯的说着，眼睛瞥了一眼林铭和颜月儿，对二人点头示意。 　　林铭、颜月儿也点头回礼，就在这时，天空中再次出现水纹一样的涟漪，麒麟族、大鹏族的族人也赶来了。 　　大鹏族的族人比上古凤族还要少，分支只有两个，金翅大鹏与青翼大鹏，这一个种族算是四神兽氏族中最弱的一支了。 　　这两大种族，每一个种族都只有十个弟子，加起来二十人，加上上古龙族和上古凤族，这次进入神兽秘境的弟子一共六十八人，这六十八人，是精英中的精英，是从一兆五千万的人口中选出来的天之骄子，每一个人，都不可小觑！ 　　林铭暗暗打量着大鹏族和麒麟族的高手，把每一个人的特点记在心中。 　　“这次秘境之行的弟子主流是命陨期，神海初期只占了少部分，神海中期的加起来没超过一只手，我的实力，对上命陨期的，都可以完胜，神海初期难说，神海中期，大概只有自保能力了，而那个龙一，我还说不准能不能逃掉……” 　　林铭在心中暗暗估算，毕竟都是绝顶人物，林铭就算实力超凡，跨越一个大境界四个小境界战斗，也是毫无抵抗之力。 　　“人都到齐了，出发！” 　　龙一仲手一甩，在他的戒指中，十五亿紫阳石飞了出来，堆了高高的一座晶山！澎湃的真元之力如同滔滔海潮，让人感受到极大的冲击。 　　而继龙一之后，萧平，还有大鹏族、麒麟族的大弟子，也纷纷将紫阳石扔出来，一时间紫光冲天，直参星斗！ 　　就在这时，只听轰隆隆的巨响，一座古老的石台从地下浮出，四座紫阳石晶山在那一刹那同时燃烧！ 　　浩瀚的能量被石台远远不断的吸收，整片天地都扭曲了起来。 　　“传送开来，大家收敛心神，因为距离比较远，对灵魂力的负荷不小。”萧平真元传音说道，下一刻，林铭只感觉一片天旋地转，这种感觉越来越强烈，仿佛要撕裂他的精神之海。 　　林铭微微蹙眉，精神力联系到精神之海中的黄金战灵，枪形战灵发出一声轻轻的嗡鸣，晕眩的感觉便完全消失了。 　　不知道过了多久的时间，林铭突然感到身体猛然一震，下一刻，他已经来到了一片蛮荒土地之上。 　　苍莽的土地，荒无人烟，连植物也没有，天空布满乌云，云层压得很低，隐隐有雷霆在云层中闪烁穿梭，偶尔闪现，如同一条白龙一般。 　　“这种地方……” 　　林铭深吸一口气，感觉在空气之中都充满了古老、苍莽的味道，因为这片空间实在沉寂的太久，至少数亿年，甚至十亿年！ 　　这就是神兽秘境，太古时代，这里或许有过让人难以想象的辉煌，无数强大的存在出入其中，可是现在，什么都逝去了，时间是最恐怖的武器，哪怕天尊、神兽，都无法承受无穷时间的洗礼！ 　　这次传送进来的六十八人都在一起，龙一蹲下身子，捏起了一把土，放在嘴里尝了尝，笑道：“我们运气不错，这片空间从来没有被探索过！这意味着更多的机缘，但也意味着更多未知的危险，诸位多加小心吧，现在，所有人可以独自探索，也可以结伴而行，出发吧，我们只有四十八个时辰的时间，之后就会被传送出去！” 　　神兽秘境地域广阔无边，而上古传送阵的传送地点却有很大的随机性，传送到一片从没被探索过的未知空间很正常。 　　“月儿师妹，在下皇洪志，人称琉炎小旋风，这种秘境探险十分危险，在下经验很多，要不月儿师妹跟在下一起吧，得到好处，我们平分。”正欲出发之际，在颜月儿身边，一个身穿锦衣的青年笑眯眯的说道。 　　颜月儿愣了一下，说实话，她虽然这些天专门经历了生死实战的训练，但是对闯这种秘境，还是一点经验都没，心里有些发憷，也确实希望有个人结伴，只不过对皇洪志，她根本不认识，与陌生人结伴，危险可想而知了。 　　想了想，上古凤族十二人中，她也只认识林铭。 　　犹豫了一下，颜月儿小声对林铭真元传音道：“那个······林师兄，我能跟你一起吗？” 　　…… 　";
        String[] texts = text.split("　　");
        StringBuilder sb = new StringBuilder();
        int len = texts.length/6;
        int i = 1;
        /*sb.append("<p>");
        for (String str : texts){
            if (i == len){
                sb.append("</p>");
                i = 1;
                sb.append("<p>");
            }
            sb.append(str);
            i++;
        }
        sb.append("</p>");
        model.addAttribute("text", sb.toString());*/
        List<String> list = new ArrayList<>();
        for (String str : texts){
            if (i == len){
                list.add(sb.toString());
                i = 1;
                sb = new StringBuilder();
            }
            sb.append(str);
            i++;
        }
        result.setData(list);
        return result;
    }

    @GetMapping("text")
    public String toRead(){
        return "text";
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("sendMessages")
    public void sendMessage(){
        KafkaMessageEntity kafkaMessageEntity = new KafkaMessageEntity();
        List<Novel> novels = novelService.queryAllBooks();
        kafkaMessageEntity.setMessage("今日推送");
        kafkaMessageEntity.setData(novels);
        kafkaMessageEntity.setDateTime(LocalDateTime.now());
        kafkaTemplate.send("xh-novel-push", "", JSON.toJSONString(kafkaMessageEntity));

    }

}

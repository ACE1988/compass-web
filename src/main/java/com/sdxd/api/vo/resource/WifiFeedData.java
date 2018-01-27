package com.sdxd.api.vo.resource;

import com.sdxd.api.vo.admin.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.resource
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/21    wenzhou.xu              Created
 */
public class WifiFeedData {
    /**
     * 模板id，必传
     * 100：无图
     * 101：小图文信息样式
     * 102：多图文样式        √
     * 103：大图文样式
     * 104：小视频文字信息样式
     * 105：大视频文字信息样式
     */
    private Integer template;
    private String id; //内容id，必传
    private String title; //内容标题，必传
    private List<String> imgs;//图片url组，可不传
    private List<Tag> tags;//标签组，可不传
    private Integer read;//阅读数，可不传
    private String comment;//是否可以评论，可不传，Y/N，默认：Y
    private String pubTime;//发布时间，可不传，YYYY-MM-DD HH:MM:SS
    private String feedTime;//推荐时间，可不传，YYYY-MM-DD HH:MM:SS
    private String url; //目标url，必传
    private String clickDc;//点击打点url,可不传
    private String inview;//入屏打点url,可不传
    private String showDc;//展示打点url,可不传
    private List<Dislike> dislike;//不感兴趣组，可不传，最多6条

    public WifiFeedData(Feed feed) {
        template = feed.getTemplate();
        id = feed.getId();
        title = feed.getTitle();
        imgs = feed.getImgList();
        tags = new ArrayList<>();
        tags.add(new Tag(3, feed.getTag()));
        tags.add(new Tag(feed.getFrom()));
        comment = feed.getComment() == 1 ? "Y" : "N";
        url = feed.getFeedUrl();
    }

    public Integer getTemplate() {
        return template;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(String feedTime) {
        this.feedTime = feedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClickDc() {
        return clickDc;
    }

    public void setClickDc(String clickDc) {
        this.clickDc = clickDc;
    }

    public String getInview() {
        return inview;
    }

    public void setInview(String inview) {
        this.inview = inview;
    }

    public String getShowDc() {
        return showDc;
    }

    public void setShowDc(String showDc) {
        this.showDc = showDc;
    }

    public List<Dislike> getDislike() {
        return dislike;
    }

    public void setDislike(List<Dislike> dislike) {
        this.dislike = dislike;
    }

    class Tag {
        /**
         * tag的模板id，可不传
         * 4：红框文字
         * 5：蓝框文字
         */
        private Integer id;
        private String text;//tag的文字，可不传

        public Tag(String text) {
            this.text = text;
        }

        public Tag(Integer id, String text){
            this.id = id;
            this.text = text;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    class Dislike {
        private String id;//不感兴趣内容编号，可不传
        private String text;//不感兴趣内容描述，可不传

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

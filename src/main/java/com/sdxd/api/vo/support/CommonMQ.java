package com.sdxd.api.vo.support;

import rx.Observable;

import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.support
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/27     melvin                 Created
 */
public class CommonMQ {

    /**
     * MQ 生产者
     */
    private String producer;
    /**
     * MQ Topic
     */
    private String topic;
    /**
     * MQ Tag
     */
    private String tag;
    /**
     * MQ 要发送的消息 (JSON)
     */
    private List<String> messages;

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getMessages() {
        if (messages != null) {
            messages = Observable.from(messages).
                    map(m -> m.replaceAll("\\\\", "")).
                    map(m -> {
                        if (m.startsWith("\"")) {
                            m = m.substring(0, 1);
                        }
                        if (m.endsWith("\"")) {
                            m = m.substring(m.length() - 1, m.length());
                        }
                        return m;
                    }).
                    toList().toBlocking().first();
        }
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}

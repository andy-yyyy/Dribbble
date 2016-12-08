/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.baesapp.beans;

import java.util.List;

/**
 * Created by lixn on 2016/12/8.
 */

public class ShotInfo {
    private int id;
    private String title;
    private String description;
    private int width;
    private int height;
    private Images images;
    private int viewsCount;
    private int likesCount;
    private int commentsCount;
    private int attachmentsCount;
    private int reboundsCount;
    private int bucketsCount;
    private String createTime;
    private String udpateTime;
    private String htmlUrl;
    private String attachementsUrl;
    private String bucketsUrl;
    private String commentsUrl;
    private String likes_url;
    private String projectsUrl;
    private String reboundsUrl;
    private boolean animated;
    private List<String> tags;
    private User user;
    private Team team;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Images getImages() {
        return images;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getAttachmentsCount() {
        return attachmentsCount;
    }

    public int getReboundsCount() {
        return reboundsCount;
    }

    public int getBucketsCount() {
        return bucketsCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUdpateTime() {
        return udpateTime;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getAttachementsUrl() {
        return attachementsUrl;
    }

    public String getBucketsUrl() {
        return bucketsUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public String getProjectsUrl() {
        return projectsUrl;
    }

    public String getReboundsUrl() {
        return reboundsUrl;
    }

    public boolean isAnimated() {
        return animated;
    }

    public List<String> getTags() {
        return tags;
    }

    public User getUser() {
        return user;
    }

    public Team getTeam() {
        return team;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void setAttachmentsCount(int attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }

    public void setReboundsCount(int reboundsCount) {
        this.reboundsCount = reboundsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        this.bucketsCount = bucketsCount;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUdpateTime(String udpateTime) {
        this.udpateTime = udpateTime;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public void setAttachementsUrl(String attachementsUrl) {
        this.attachementsUrl = attachementsUrl;
    }

    public void setBucketsUrl(String bucketsUrl) {
        this.bucketsUrl = bucketsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public void setProjectsUrl(String projectsUrl) {
        this.projectsUrl = projectsUrl;
    }

    public void setReboundsUrl(String reboundsUrl) {
        this.reboundsUrl = reboundsUrl;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

package com.ksyun.campus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @Author: Lemon
 * @Date: 2024/7/8 10:06
 */
public class Player {
    private List<Integer> handCards = new ArrayList<Integer>();

    private List<Integer> score = new ArrayList<Integer>();

    private List<Integer> openCards = new ArrayList<Integer>();


    public void dealCards(Integer card){
        //发牌
        handCards.add(card);
    }

    public PickCard pickCards(Integer playerTotalNum) {
        //玩家挑选玩家并从中挑选 最大或最小，或者选择公共区域的卡片
        //0 代表公共区域 1 代表1号玩家，以此类推
        //这里采用全随机策略
        //随机选择大小
        boolean maxOrMin;
        int i = RandomUtil.randomInt(0, 1);
        if (i == 0){
            maxOrMin = false;
        }else {
            maxOrMin = true;
        }
        PickCard pickCard = new PickCard(RandomUtil.randomInt(0,playerTotalNum),maxOrMin);
        return pickCard;
    }

    public Integer getMaxCards(){
        return Collections.max(handCards);
    }

    public Integer getMinCards(){
        return Collections.min(handCards);
    }

    //获取翻出的卡
    public void getCards(Integer card){
        openCards.add(card);
    }

    //移除一张卡
    public void removeCard(Integer card){
        handCards.remove(card);
    }



    public List<Integer> getHandCards() {
        return handCards;
    }

    public void setHandCards(List<Integer> handCards) {
        this.handCards = handCards;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    public List<Integer> getOpenCards() {
        return openCards;
    }

    public void setOpenCards(List<Integer> openCards) {
        this.openCards = openCards;
    }
}

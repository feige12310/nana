package com.ksyun.campus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @Author: Lemon
 * @Date: 2024/7/8 10:01
 */
public class Game {
    List<Integer> gameCards = new ArrayList<Integer>();

    List<Integer> publicCards = new ArrayList<Integer>();

    List<Integer> openCards = new ArrayList<Integer>();

    List<Player> players = new ArrayList<Player>();

    int gamePlayer;

    int currentPlayer;

    boolean gameOver;

    public Game(int gamePlayer) throws Exception {
        this.gamePlayer = gamePlayer;
        initGame(gamePlayer);
    }


    private void initGame(int gamePlayer) throws Exception {
        //根据游戏人数初始化游戏
        if (gamePlayer <= 1 && gamePlayer > 5){
            throw new Exception("游戏人数必须在2-5人之间");
        }
        switch (gamePlayer) {
            case 2:
                //2人去掉11跟12
                for (int i = 1; i < 11 ; i++) {
                    //每个数字加三次
                    for (int j = 1; j < 4; j++) {
                        gameCards.add(i);
                    }
                }
                //添加玩家
                for (int i = 0; i < 2 ; i++) {
                    Player player = new Player();
                    players.add(player);
                }
                break;
            case 3:
                //3人去掉 12 todo
                break;
            case 4:
                //4人不用去掉 todo
                break;
            case 5:
                //5人不用去掉 todo
                break;
        }
        dealCard();
    }

    private void dealCard(){
        //洗牌
        int totalNum = gameCards.size();
        //进行50次随机交换
        for (int i = 0; i < 50; i++) {
            ListUtil.swapElement(gameCards, gameCards.get(RandomUtil.randomInt(0,totalNum)),gameCards.get(RandomUtil.randomInt(0,totalNum)));
        }
        //将牌发给玩家
        for (int i = 0; i < gameCards.size(); i++) {
            //发20张给玩家
            if(i < 20){
                players.get(i%players.size()).dealCards(gameCards.get(i));
            }else{
                //发最后10张给公共区域
                publicCards.add(gameCards.get(i));
            }
        }
    }

    public void startGame(){
        //首先显示当前游戏情况
        //显示公共区域卡片
        System.out.println("公共区域卡是"+publicCards);
        //显示玩家的卡片
        for (int i = 0; i < players.size(); i++) {
            ListUtil.sort(players.get(i).getHandCards(), Comparator.naturalOrder());
        }
        //未检测到游戏结束，则一直继续
        currentPlayer = 0;
        while (gameOver){
            //双方玩家轮流进行回合
            System.out.println("当前是"+(currentPlayer+1)+"玩家行动");
            PickCard pickCard = players.get(currentPlayer).pickCards((players.size() + 1));
            //根据pickCard 返回对应的值
            switch (pickCard.playerNum){
                case 0:
                    //公共区域随机一张
                    Integer card = publicCards.get(RandomUtil.randomInt(0, publicCards.size()));
                    openCards.add(card);
                    players.get(currentPlayer).getCards(card);
                    break;
                case 1:
                    //获取一号玩家一张卡
                    break;
                case 2:
                    //获取二号玩家一张卡
                    break;
            }
            //判断当前玩家是否结束
            if(openCards.size()==1){
                //直接继续
                continue;
            }else if(openCards.size()==2){
                if(openCards.get(0).equals(openCards.get(1))){
                    continue;
                }else{
                    //清空opencard
                    reset();
                    //轮到下一个名玩家
                    currentPlayer = currentPlayer + 1;
                    if(currentPlayer == players.size()){
                        currentPlayer = 0;
                    }
                }
            }

        }
    }

    public void reset(){
        //清空翻开的卡
        openCards.removeAll(openCards);
    }

}

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
        //������Ϸ������ʼ����Ϸ
        if (gamePlayer <= 1 && gamePlayer > 5){
            throw new Exception("��Ϸ����������2-5��֮��");
        }
        switch (gamePlayer) {
            case 2:
                //2��ȥ��11��12
                for (int i = 1; i < 11 ; i++) {
                    //ÿ�����ּ�����
                    for (int j = 1; j < 4; j++) {
                        gameCards.add(i);
                    }
                }
                //������
                for (int i = 0; i < 2 ; i++) {
                    Player player = new Player();
                    players.add(player);
                }
                break;
            case 3:
                //3��ȥ�� 12 todo
                break;
            case 4:
                //4�˲���ȥ�� todo
                break;
            case 5:
                //5�˲���ȥ�� todo
                break;
        }
        dealCard();
    }

    private void dealCard(){
        //ϴ��
        int totalNum = gameCards.size();
        //����50���������
        for (int i = 0; i < 50; i++) {
            ListUtil.swapElement(gameCards, gameCards.get(RandomUtil.randomInt(0,totalNum)),gameCards.get(RandomUtil.randomInt(0,totalNum)));
        }
        //���Ʒ������
        for (int i = 0; i < gameCards.size(); i++) {
            //��20�Ÿ����
            if(i < 20){
                players.get(i%players.size()).dealCards(gameCards.get(i));
            }else{
                //�����10�Ÿ���������
                publicCards.add(gameCards.get(i));
            }
        }
    }

    public void startGame(){
        //������ʾ��ǰ��Ϸ���
        //��ʾ��������Ƭ
        System.out.println("����������"+publicCards);
        //��ʾ��ҵĿ�Ƭ
        for (int i = 0; i < players.size(); i++) {
            ListUtil.sort(players.get(i).getHandCards(), Comparator.naturalOrder());
        }
        //δ��⵽��Ϸ��������һֱ����
        currentPlayer = 0;
        while (gameOver){
            //˫������������лغ�
            System.out.println("��ǰ��"+(currentPlayer+1)+"����ж�");
            PickCard pickCard = players.get(currentPlayer).pickCards((players.size() + 1));
            //����pickCard ���ض�Ӧ��ֵ
            switch (pickCard.playerNum){
                case 0:
                    //�����������һ��
                    Integer card = publicCards.get(RandomUtil.randomInt(0, publicCards.size()));
                    openCards.add(card);
                    players.get(currentPlayer).getCards(card);
                    break;
                case 1:
                    //��ȡһ�����һ�ſ�
                    break;
                case 2:
                    //��ȡ�������һ�ſ�
                    break;
            }
            //�жϵ�ǰ����Ƿ����
            if(openCards.size()==1){
                //ֱ�Ӽ���
                continue;
            }else if(openCards.size()==2){
                if(openCards.get(0).equals(openCards.get(1))){
                    continue;
                }else{
                    //���opencard
                    reset();
                    //�ֵ���һ�������
                    currentPlayer = currentPlayer + 1;
                    if(currentPlayer == players.size()){
                        currentPlayer = 0;
                    }
                }
            }

        }
    }

    public void reset(){
        //��շ����Ŀ�
        openCards.removeAll(openCards);
    }

}

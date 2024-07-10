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

    List<OpenCard> openCards = new ArrayList<OpenCard>();

    List<Player> players = new ArrayList<Player>();

    int gamePlayer;

    int currentPlayer;

    boolean isGameOver;

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
            System.out.println("��"+(i+1)+"����ҵ�����"+players.get(i).getHandCards());
        }
        //δ��⵽��Ϸ��������һֱ����
        currentPlayer = 0;
        while (!isGameOver){
            //˫������������лغ�
            System.out.println("��ǰ��"+(currentPlayer+1)+"����ж�");
            PickCard pickCard = players.get(currentPlayer).pickCards((players.size() + 1));
            Integer playerC = 0;
            //����pickCard ���ض�Ӧ��ֵ
            switch (pickCard.playerNum){
                case 0:
                    //�����������һ��
                    int publicN = RandomUtil.randomInt(0, publicCards.size());
                    Integer card = publicCards.get(publicN);
                    openCards.add(new OpenCard(0,card));
                    System.out.println("���"+(currentPlayer+1)+"�ŷ�����������һ����"+card);
                    players.get(currentPlayer).getCards(card);
                    break;
                case 1:
                    //��ȡһ�����һ�ſ�
                    if (pickCard.maxOrMin){
                        //��ȡ����һ��
                        playerC = players.get(0).getMaxCards();
                    }else{
                        playerC = players.get(0).getMinCards();
                    }
                    openCards.add(new OpenCard(1,playerC));
                    System.out.println("���"+(currentPlayer+1)+"�ŷ���1�����һ����"+playerC);
                    players.get(currentPlayer).getCards(playerC);
                    break;
                case 2:
                    //��ȡ�������һ�ſ�
                    if (pickCard.maxOrMin){
                        //��ȡ����һ��
                        playerC = players.get(1).getMaxCards();
                    }else{
                        playerC = players.get(1).getMinCards();
                    }
                    openCards.add(new OpenCard(2,playerC));
                    System.out.println("���"+(currentPlayer+1)+"�ŷ���1�����һ����"+playerC);
                    players.get(currentPlayer).getCards(playerC);
                    break;
            }
            //�жϵ�ǰ����Ƿ����
            if(openCards.size()==1){
                //ֱ�Ӽ���
                continue;
            }else if(openCards.size()==2){
                if(openCards.get(0).card.equals(openCards.get(1).card)){
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
            }else if (openCards.size()==3){
                //�ж��Ƿ�ȫ�����
                if(openCards.get(0).card.equals(openCards.get(1).card) && openCards.get(0).card.equals(openCards.get(2).card)){
                    //�÷�
                    List<Integer> score = players.get(currentPlayer).getScore();
                    score.add(openCards.get(0).card);
                    //�ж���Ϸ�Ƿ����
                    isGameOver = isGameOver(score);
                    //�Ƴ���Ƭ
                    for (int i = 0; i < openCards.size(); i++) {
                        OpenCard openCard = openCards.get(i);
                        switch (openCard.playerNum){
                            case 0:
                                //��������ɾ��һ����
                                publicCards.remove(openCard.card);
                                break;
                            case 1:
                                //�Ƴ�һ�����һ�ſ�
                                players.get(0).removeCard(openCard.card);
                                break;
                            case 2:
                                //�Ƴ��������һ�ſ�
                                players.get(1).removeCard(openCard.card);
                                break;
                        }
                    }
                    if (isGameOver){
                        //��Ϸ��������ǰ��һ�ʤ
                        System.out.println("��"+currentPlayer+"��һ�ʤ�÷�"+players.get(currentPlayer).getScore());
                    }
                    continue;
                }
            }

        }
    }

    public boolean isGameOver(List<Integer> scores){
        //�ж���Ϸ�Ƿ������Ҳ����һ������Ƿ�ӵ��7�ĵ÷֣���������ϳ�7�ĵ÷� todo
        return false;
    }

    public void reset(){
        //��շ����Ŀ�
        openCards.removeAll(openCards);
    }

}

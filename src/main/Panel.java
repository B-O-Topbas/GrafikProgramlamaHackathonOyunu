package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Panel extends JPanel implements KeyListener, ActionListener {
    Timer t;

    //klavye yönleri
    boolean up, down, left, right;
    //üretimlerin bool ifadeleri
    boolean yolCizgisi = false, varlik = false, karsiAraba = false, odul = false,createPrize = false;
    static boolean seviye;

    //zamana bağlı işlevler için kullanılan değişkenler
    long eksiltmeAraligi;
    long seviyeArtirmaAraligi;
    long puanArtirma;
    long baslangicZamani;

    int hak = 3;
    int seviyeSayisi;
    int seviyeArtir = 0;
    //duraklatmadaki klavye basış sayısı
    int basis = 0;

    //oluşma zamanı ayarları
    long yolCizgisiDelay = 300;
    long yolKenarindakiVarlikDelay = 4000;
    long karsiArabaDelay = 3000;


    //oyun devammı tamammı durumu
    boolean inGame = true;//true olacak ////////////////////////////////
    boolean duraklatildi=false;
    int geriSay=5;



    //resimler
    private BufferedImage myCar, extraPoints, backGround, kirmiziAraba, sariAraba, morAraba, yesilAraba;
    private BufferedImage kaktus, tilki, ev, demirAdam, can;
    private BufferedImage level1, level2, level3;
    private BufferedImage kupa;
    //çok olan resimlerin dizisi
    BufferedImage[] arabalar;
    BufferedImage[] yolKenarindakiVarliklar;
    BufferedImage[] leveller;

    // oluşan nesnelerin array listleri
    ArrayList<YolCizgileri> yolCizgileriArrayList = new ArrayList<>();
    ArrayList<KarsiAraba> karsiArabaArrayList = new ArrayList<>();
    ArrayList<Prize> prizeArrayList = new ArrayList<>();
    ArrayList<YolKenarindakiVarlik> yolKenarindakiVarlikArrayList = new ArrayList<>();

    //Threadler
    YolCizgileriUret yolCizgileriUret;
    KarsiArabaUret karsiArabaUret;
    PrizeUret prizeUret;
    VarlikUret varlikUret;
    SeviyeYukselt seviyeYukselt;

    // karşıdan gelen arabanın sol şeritmi sağ şertittenmi gelme ayarı
    int[] karsiArabaXCoordinate = {130, 240};

    //    yol kenarlarında oluşan varlıkların oluşma koordinatları
    int[] varlikXCoordinates = {30, 350};

    //total puan
    int puan = 0;//0 olacak //////////////////
    //kontrol altında olan nesneler klavye !!
    Araba arabam;
    //kullanılan arabanın hızları
    int dx = 2, dy = 2;
    //araba boş iken geriye gitmesini sağlama
    int arabamBosDy = 1;
    //karşıdan gelen arabaların gitme hızı
    int opDy = 2;
    //oluşan çizginin geçme hızı
    int cizgiHiz = 5;

    public Panel() {
        setFocusable(true);
        addKeyListener(this);
        loadPictures();
        arabam = new Araba(240, 528, 50, 100);
//        karsiAraba = new KarsiAraba(150, 528, 60, 100, true);
        baslangicZamani = System.currentTimeMillis();
        eksiltmeAraligi = System.currentTimeMillis();
        seviyeArtirmaAraligi = System.currentTimeMillis();
        puanArtirma=System.currentTimeMillis();
        t = new Timer(1, this);
        t.start();

        //threadlerin oluşması
        yolCizgileriUret = new YolCizgileriUret();
        karsiArabaUret = new KarsiArabaUret();
        prizeUret = new PrizeUret();
        varlikUret = new VarlikUret();
        seviyeYukselt = new SeviyeYukselt();

        //çoklu resimleri tutan dizilerin ilklendirilmesi
        arabalar = new BufferedImage[]{kirmiziAraba, sariAraba, yesilAraba, morAraba};
        yolKenarindakiVarliklar = new BufferedImage[]{tilki, ev, kaktus, demirAdam};
        leveller = new BufferedImage[]{level1, level2, level3};

        //threadlerin başlaması
        yolCizgileriUret.start();
        varlikUret.start();
        karsiArabaUret.start();
        prizeUret.start();
        seviyeYukselt.start();
    }
    private void loadPictures() {
        try {
            extraPoints = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\resimler\\prize.png"));
            backGround = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\resimler\\arkaplan.png"));
            myCar = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\resimler\\myCar.png"));
            kirmiziAraba = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\resimler\\oppenentCar.png"));
            yesilAraba = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\arabalar\\yesilaraba.png"));
            morAraba = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\arabalar\\moraraba.png"));
            sariAraba = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\arabalar\\sarıaraba.png"));
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            can = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\yolSenlikleri\\can.png"));
            tilki = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\yolSenlikleri\\fox.png"));
            ev = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\yolSenlikleri\\home.png"));
            kaktus = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\yolSenlikleri\\cactus.png"));
            demirAdam = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\yolSenlikleri\\ironMan.png"));
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            level1 = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\resimler\\level3-PhotoRoom.png-PhotoRoom.png"));
            level2 = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\resimler\\level2-PhotoRoom.png-PhotoRoom.png"));
            level3 = ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\resimler\\level1-PhotoRoom.png-PhotoRoom.png"));
            kupa=ImageIO.read(new File("C:\\Users\\dilek\\IdeaProjects\\Hackathon\\src\\seviyeler\\Golden-Cup.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            ciz(g);
        } else {
            gameOver(g);
        }
        if (duraklatildi){
            geriSayim(g);
        }
    }

    private void geriSayim(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getSize().width, getSize().height);
        g2d.setColor(Color.white);
        var small = new Font("Helvetica", Font.BOLD, 16);
        g2d.setFont(small);
        g2d.drawString(String.valueOf(geriSay),50,50);
        repaint();
    }

    private void gameOver(Graphics g) {
        yolCizgisi = true;
        varlik = true;
        odul = true;
        karsiAraba = true;
        seviye = true;
        t.stop();
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getSize().width, getSize().height);
        g2d.setColor(Color.white);
        var small = new Font("Helvetica", Font.BOLD, 16);
        g2d.setFont(small);

        File file = new File("C:\\Users\\dilek\\OneDrive\\Masaüstü\\enYuksekPuan.txt");
        String line;
        int enYuksekPuan = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                enYuksekPuan = Integer.parseInt(line);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            if (puan > enYuksekPuan) {
                g2d.drawImage(kupa,150,225,120,120,this);
                g2d.drawString("TEBRİKLER REKOR KIRDINIZ", 120, 200);
                g2d.drawString("Yeni En Yüksek Puan: " + puan, 125, 380);
                writer.write(String.valueOf(puan));
            } else {

                g2d.drawString("KAYBETTİNİZ 🙁", 150, 200);
                g2d.drawString("En Yüksek Puan: " + enYuksekPuan, 125, 220);
                g2d.drawString("Kazandığınız Puan: "+ puan ,125,240);
                writer.write(String.valueOf(enYuksekPuan));
            }
            writer.flush();
            writer.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ciz(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
//        System.out.println("Width= "+getSize().getWidth()+" Heith= "+getSize().getHeight());

        g2d.drawImage(backGround, 0, 0, getSize().width, getSize().height, this);
        g2d.setColor(Color.gray);


        if (System.currentTimeMillis() - baslangicZamani >= 10000) {
            createPrize = true;
            baslangicZamani = System.currentTimeMillis();
        }
        for (Prize prize : prizeArrayList) {
            if (prize.y > 644) {
                prize.visible = false;
            }
        }
        for (Prize prize2 : prizeArrayList) {
            if (prize2.intersects(arabam)) {
                prize2.visible = false;
                puan += 10;
            }
        }
        if ((System.currentTimeMillis()-puanArtirma)>2000){
            puan+=2;
            puanArtirma=System.currentTimeMillis();
        }



        for (YolCizgileri cizgi3 : yolCizgileriArrayList) {
            if (cizgi3.y > 644) {
                cizgi3.visible = false;
            }
        }
        for (KarsiAraba karsiAraba1 : karsiArabaArrayList) {
            if (karsiAraba1.y > 644) {
                karsiAraba1.setVisible(false);
            }
        }

        for (YolKenarindakiVarlik varlik1 : yolKenarindakiVarlikArrayList) {
            if (varlik1.y > 644) {
                varlik1.visible = false;
            }
        }
        for (YolKenarindakiVarlik varlik2 : yolKenarindakiVarlikArrayList) {
            if (varlik2.visible) {
                g2d.drawImage(yolKenarindakiVarliklar[varlik2.resim], varlik2.x, varlik2.y, varlik2.w, varlik2.h, this);
            }
        }
        for (YolCizgileri cizgi : yolCizgileriArrayList) {
            if (cizgi.visible) {
                g2d.fillRect(cizgi.x, cizgi.y, cizgi.w, cizgi.h);
            }
        }

        for (KarsiAraba karsiAraba2 : karsiArabaArrayList) {
            if (karsiAraba2.visible) {
//                g2d.fillRoundRect((int) karsiAraba2.x, (int) karsiAraba2.y, (int) karsiAraba2.width, (int) karsiAraba2.height,20,170);
//                g2d.fillRect((int) karsiAraba2.x, (int) karsiAraba2.y, (int) karsiAraba2.width, (int) karsiAraba2.height);
                g2d.drawImage(arabalar[karsiAraba2.arabaResmi], (int) karsiAraba2.x - 10, (int) karsiAraba2.y, 70, 100, this);
            }
        }

        for (Prize prize2 : prizeArrayList) {
            if (prize2.visible) {
                g2d.drawImage(extraPoints, (int) prize2.x, (int) prize2.y, (int) prize2.width, (int) prize2.height, this);
            }
        }

        prizeArrayList.removeIf(prize -> !prize.visible);
        yolCizgileriArrayList.removeIf(yolCizgileri -> !yolCizgileri.visible);
        karsiArabaArrayList.removeIf(karsiAraba -> !karsiAraba.visible);

        if (up) {
            arabam.substractY(dy);
        }
        if (down && arabam.y < 528) {
            arabam.addY(dy);
        }
        if (right && arabam.x < 280) {
            arabam.addX(dx);
        }
        if (left && arabam.x > 100) {
            arabam.substractX(dx);
        }

//        System.out.println(arabam.y);
//        g2d.fillRoundRect((int) arabam.x, (int) arabam.y, (int) arabam.width, (int) arabam.height,20,170);
//        g2d.fillRect((int) arabam.x, (int) arabam.y, (int) arabam.width, (int) arabam.height);
        g2d.drawImage(myCar, (int) arabam.x, (int) arabam.y, (int) arabam.width, (int) arabam.height, this);//w 50 h 100


//        if (karsiAraba.visible) {
//            g2d.drawImage(againstCar, (int)karsiAraba.x,(int)karsiAraba.y,70,(int)karsiAraba.height, null);//w 70 h 100
//        }
//        g2d.drawImage(extraPoints,150,30,50,50,null);

        g2d.drawImage(leveller[seviyeSayisi], 356, 594, 40, 30, this);//408, 510, 20, 18


        if (hak == 3) {
            g2d.drawImage(can, 345, 500, 150, 150, this);
            g2d.drawImage(can, 345, 520, 150, 150, this);
            g2d.drawImage(can, 345, 540, 150, 150, this);
        }
        if (hak == 2) {
            g2d.drawImage(can, 345, 520, 150, 150, this);
            g2d.drawImage(can, 345, 540, 150, 150, this);
        }
        if (hak == 1) {
            g2d.drawImage(can, 345, 540, 150, 150, this);
        }
        if (hak == 0) {
            inGame = false;
        }


        for (KarsiAraba karsiAraba1 : karsiArabaArrayList) {
            if (karsiAraba1.intersects(arabam)) {
                if (System.currentTimeMillis() - eksiltmeAraligi > 3000) {
                    hak--;
                    eksiltmeAraligi = System.currentTimeMillis();
                }
                System.out.println("Çakıştı");
            }
        }

//
//        if (sayac == 8) {
//            yolCizgisiDelay = 250;
//            cizgiHiz = 7;
//        }
//
//        if (sayac == 16) {
//            yolCizgisiDelay = 230;
//            cizgiHiz = 10;
//        }


        //150 ,30,50,100 sol şerit ortalama
        //240 sağ şerit ortalama arablar için !!
        //150,30,60,100 oppenent car
        //210,30,10,60 çizgilerin kordinatı
        //150,30,50,50 ödül ayarları
        g2d.setColor(Color.white);

        g2d.drawString(String.valueOf(puan), 414, 530);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        for (YolCizgileri cizgi2 : yolCizgileriArrayList) {
            cizgi2.addY(cizgiHiz);
        }
        for (KarsiAraba karsiAraba4 : karsiArabaArrayList) {
            karsiAraba4.addY(opDy);
        }

        for (Prize prize3 : prizeArrayList) {
            prize3.addY(2);
        }

        for (YolKenarindakiVarlik varlik3 : yolKenarindakiVarlikArrayList) {
            varlik3.addY(cizgiHiz);
        }
        if (!up && !down) {
            if (arabam.y < 528) arabam.addY(arabamBosDy);
        }

        repaint();
    }

    public void duraklat() {
        yolCizgisi = true;
        varlik = true;
        odul = true;
        karsiAraba = true;
        seviye = true;
        t.stop();
    }

    public void devamEttir() {
        yolCizgisi = false;
        varlik = false;
        odul = false;
        karsiAraba = false;
        seviye = false;
        yolCizgileriUret = new YolCizgileriUret();
        varlikUret = new VarlikUret();
        karsiArabaUret = new KarsiArabaUret();
        prizeUret = new PrizeUret();
        yolCizgileriUret.start();
        varlikUret.start();
        karsiArabaUret.start();
        prizeUret.start();
        t.start();
        seviyeYukselt = new SeviyeYukselt();
        seviyeYukselt.start();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            basis++;
            if (basis == 1) {
                duraklat();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            if (basis == 2) {
               duraklatildi=true;
                devamEttir();
                basis = 0;
                duraklatildi=false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
    }


    class YolCizgileriUret extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                yolCizgileriArrayList.add(new YolCizgileri(210, -30, 10, 60, true));
                try {
                    Thread.sleep(yolCizgisiDelay);
                    if (yolCizgisi) {
                        Thread.currentThread().interrupt();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    class KarsiArabaUret extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                int i = (int) (Math.random() * 2);
                int secim = (int) (Math.random() * 4);
                karsiArabaArrayList.add(new KarsiAraba(karsiArabaXCoordinate[i], -30, 50, 97, true, secim));

                try {
                    Thread.sleep(karsiArabaDelay);
                    if (karsiAraba) {
                        Thread.currentThread().interrupt();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    class PrizeUret extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                int j = (int) (Math.random() * 2);
                if (createPrize) {
                    prizeArrayList.add(new Prize(karsiArabaXCoordinate[j], -50, 50, 50, true));
                    createPrize = false;
                }
                try {
                    Thread.sleep(1000);
                    if (odul) {
                        Thread.currentThread().interrupt();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    class VarlikUret extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                int x = (int) (Math.random() * 2);
                int resim = (int) (Math.random() * 4);
                yolKenarindakiVarlikArrayList.add(new YolKenarindakiVarlik(varlikXCoordinates[x], -30, 50, 50, true, resim));
                try {
                    Thread.sleep(yolKenarindakiVarlikDelay);
                    if (varlik) {
                        Thread.currentThread().interrupt();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    class SeviyeYukselt extends Thread {
        @Override
        public void run() {
            while (seviyeArtir < 3) {
                System.err.println(seviyeArtir);
                if (seviyeArtir == 0) {
                    yolCizgisiDelay = 300;// buranın ayarı tamam
                    cizgiHiz = 7; // buranın ayarı da tamam
                    karsiArabaDelay=3000;
                    opDy=6;
                    seviyeSayisi = 0;
                }
                if (seviyeArtir == 1) {
                    yolCizgisiDelay = 200;//250//200 olacak
                    cizgiHiz = 10;//7//10 olacak
                    dx=4;
                    dy=4;
                    arabamBosDy=2;
                    karsiArabaDelay=980;
                    opDy=8;
                    seviyeSayisi = 1;
                }
                if (seviyeArtir == 2) {
                    yolCizgisiDelay = 130;//220//150 de güzel di // burası 110//130 olacak
                    cizgiHiz = 16;//12//20 //16olacak
                    dx=6;
                    dy=6;
                    arabamBosDy=3;
                    karsiArabaDelay=620;
                    opDy=10;
                    seviyeSayisi = 2;
                }
                System.out.println("MErhaba");
                if ((System.currentTimeMillis() - seviyeArtirmaAraligi) > 15000) {
                    seviyeArtir++;
                    seviyeArtirmaAraligi = System.currentTimeMillis();
                }
                try {
                    Thread.sleep(3000);
                    if (seviye) {
                        Thread.currentThread().interrupt();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}



/*
sleep metodun dan sonra interraupt yap bunu bool ifade ile kontrol et yani duraklatma veya oyun bittiğinde set true yapacağız 4 değişken gerekiyor.✅

Çarpma hakkı 3 yap yani 3 can olacak ✅

gazı kestiğinde geriye doğru yapışacak (👉ﾟヮﾟ)👉 ✅

öldükten sonra ekranda en yüksek puanı göstereceksin eğer yeni puan en yüksek puandan fazla ise txt dosyasındaki değeri değiştireceksin ✅

ekrana seviye yazılacak 3 kademe olacak seviyeler✅

her 5 sn de +2 puan ekle ; ✅

kupa ve birazda renklendir ✅

yol çizgisinin hızı yol kenarında ki varlıkların hızını ve karşı arabaların hızlarını sekron yap ✅

yol kenarlarındaki varlıkları biraz daha çeşitlendir

yapabilirsen duraklatma sonrasında devam tuşuna basınca geriye sayma yap;
 */
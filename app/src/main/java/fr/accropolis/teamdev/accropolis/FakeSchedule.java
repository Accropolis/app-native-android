package fr.accropolis.teamdev.accropolis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import fr.accropolis.teamdev.accropolis.model.Live;

/**
 * Created by Nicolas Padiou on 25/02/17.
 */

public class FakeSchedule {
    public static List<Live> loadSchedule(){

        Live live = null;
        String uriImage= "";
        String title = "";
        String content = "";
        Calendar calendar = Calendar.getInstance();
        List<Live> schedule = new ArrayList<>();

        //trudeau
         uriImage= "http://accropolis.fr/wp-content/uploads/2016/05/1.3.png";
         title = "Discours de Justin Trudeau";
         content = "Le beau Justin fait un discour de tabernacle.";
         calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        live =new Live(uriImage, title, content, calendar);
        schedule.add(live);


        //QAG
        uriImage= "http://accropolis.fr/wp-content/uploads/2016/05/Screnshot-QAG-Accro.png";
        title = "Questions au gouvernement";
        content = "Chaque mardi et mercredi de 15h à 16h, les députés contrôlent l'action du gouvernement en interrogeant ses ministres sur des sujets d'actualité. Accropolis propose de suivre cette séance en stream, agrémenté de commentaires de datas utiles à la compréhension des débats. ";
        calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 0);
        live =new Live(uriImage, title, content, calendar);
        schedule.add(live);



        //QAG
        uriImage= "http://accropolis.fr/wp-content/uploads/2016/05/Screnshot-QAG-Accro.png";
        title = "Questions au gouvernement";
        content = "Chaque mardi et mercredi de 15h à 16h, les députés contrôlent l'action du gouvernement en interrogeant ses ministres sur des sujets d'actualité. Accropolis propose de suivre cette séance en stream, agrémenté de commentaires de datas utiles à la compréhension des débats. ";
        calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 0);
        live =new Live(uriImage, title, content, calendar);
        schedule.add(live);

        //inside accropolis
        uriImage= "http://accropolis.fr/wp-content/uploads/2016/05/Logo-Inside-Accropolis.jpg";
        title = "Inside Accropolis";
        content = "C'est quoi Accropolis ? Ça fonctionne comment ? Et t'es qui toi avec tes lunettes qui nous parle tout le temps de politique ? Dans Inside Accroppolis, on prend le temps de se poser, de discuter entre nous du projet, de son fonctionnement, de son développement et de sa communauté grandissante. C'est le moment de poser ses questions, de suggérer des améliorations et de proposer son aide !";
        calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+2);
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        live =new Live(uriImage, title, content, calendar);
        schedule.add(live);

        //libre antenne
        uriImage= "http://accropolis.fr/wp-content/uploads/2016/05/Logo-Libre-Antenne.png";
        title = "Libre Antenne";
        content = "Ce soir c'est libre Antenne avec Difoul!!!";
        calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+2);
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        live =new Live(uriImage, title, content, calendar);
        schedule.add(live);

        //libre antenne
        uriImage= "http://accropolis.fr/wp-content/uploads/2016/05/bouge_ta_democratie_logo.png";
        title = "Bouge ta démocratie !";
        content = "Dans le cadre d'une expérimentation de la “démocratie permanente” en Région Centre-Val de Loire, Accropolis vous propose de suivre la \"caravane citoyenne\" de Territoires Hautement Citoyens. Nous ferons le point régulièrement sur cette campagne de terrain visant à aller à la rencontre des habitants et des acteurs de la société civile de la région pour co-construire une démocratie participative.";
        calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+3);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 0);
        live =new Live(uriImage, title, content, calendar);
        schedule.add(live);



        return schedule;
    }


    public static List<Live> today(){

        List<Live> schedule = loadSchedule();
        List<Live> today = new ArrayList();
        Calendar now = Calendar.getInstance();

        for(Iterator<Live> live = schedule.iterator(); live.hasNext(); ) {
            Live item = live.next();

            if(item.getDate().get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)){
                today.add(item);
            }
        }

        return today;
    }
}

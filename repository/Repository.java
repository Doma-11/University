/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.repository;

import hr.algebra.model.Game;
import hr.algebra.model.Player;

import hr.algebra.model.Team;
import hr.algebra.model.User;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author boric
 */
public class Repository {
    
    private static final ObservableList<User> USERS= FXCollections.observableArrayList();
    
    private static  User THIS_USER;
    private static  User PLAYS_FIRST;
    private static Game GAME;
    
    
    
    public static ObservableList<User> getUsers(){
        return USERS;
    }
    
    public static User getUser(String nickname){
        return (User)USERS.stream().filter(u-> u.getNickName().equals(nickname)).findFirst().orElse(null);
    }
    
    public static void addUser(User user){
        USERS.add(user);
    }
    
    public static void deleteUsers(){
        USERS.clear();
    }
    public static void addThisUser(User user){
        THIS_USER=user;
    }
    public static User getThisUser(){
        return THIS_USER;
    }
    public static void deleteThisUser(){
        THIS_USER=null;
    }
    public static void setGameObject(Game game){
        GAME=game;
    }
    public static Game getGameObject(){
        return GAME;
    }
    public static User getFirstToPlay(){
        if(PLAYS_FIRST== null){
            PLAYS_FIRST=getUsers().sorted().get(0);
        }
        return PLAYS_FIRST;
    }
    
    public static void deleteFrstToPlay(){
        PLAYS_FIRST=null;
    }
    
    public static void addPlayerstoUserTeam(User user, ObservableList<Player> players){
        if(user.getPlayersInHand()!=null){
            ObservableList<Player> selectedPlayers= FXCollections.observableArrayList(user.getPlayersInHand());
            players.forEach(p-> selectedPlayers.add(p));
        }
        else{
            user.setPlayersInHand(players);
        }
    }
    
    public static void addPointsToUser(User user, double points){
       double sum= user.getPoints()+ points;
       user.setPoints(sum);
    }
    
    
    
    private static final ObservableList<Team> TEAMS=FXCollections.observableArrayList();
    
    
    public static ObservableList<Player> getGoalkeepers(){
        return GOALKEEPERS;
    }
    public static ObservableList<Player> getDefenders(){
        return DEFENDERS;
    }
    public static ObservableList<Player> getMidfielders(){
        return MIDFIELDERS;
    }
     public static ObservableList<Player> getAttackers(){
        return ATTACKERS;
    }
     
     public static void addNewTeam( Team team){
        TEAMS.add(team);
     }
     
     
     
     public static ObservableList<Team> getTeams(){
         return TEAMS;
     }
     
    
     public static final ObservableList<Player> GOALKEEPERS= FXCollections.observableArrayList(          
            new Player ("Emiliano","Martínez", 6.74,"EMILIANOMARTINEZ.jpg",false),
            new Player ("Unai","Simón", 6.73,"UNAISIMON.jpg",false),
            new Player ("Dominik", "Livakovic",7.33,"LIVAKOVIC.jpg",false),
            new Player ("Wojciech","Szczęsny", 7.93,"SCEZNY.jpg",false),
            new Player ("Andries","Noppert",7.28,"NOPPERT.jpg",false),
            new Player ("Hugo","Lloris",6.92,"LLORIS.jpg",false),                      
            new Player ("Vanja","Milinkovic-Savic",6.90,"VANJAMILINKOVIC-SAVIC.jpg",false),
            new Player ("Shuichi","Gonda",6.85,"GONDA.jpg",false),
            new Player ("Mohammed ","Al-Owais",7.40,"AL-OWAIS.jpg",false),            
            new Player ("Mathew","Ryan",6.83,"MATTHEWRYAN.jpg",false),
            new Player ("Kim","Seung-gyu ",6.68,"SEUNG-GYU.jpg",false),
            new Player ("Keylor"," Navas",6.30,"NAVAS.jpg",false),
            new Player ("Manuel","Neuer",6.23,"NEUER.jpg",false),
            new Player ("Kasper","Schmeichel",7.03,"SCHMEICHEL.jpg",false),            
            new Player ("Fernando","Muslera",6.90,"MUSLERA.jpg",false)           
    
    );        
    
    
    public static ObservableList<Player> DEFENDERS= FXCollections.observableArrayList(
        
            new Player ("Virgil","Van Dijk",7.08,"VANDIJK.jpg",false),
            new Player ("Daley","Blind",7.38,"BLIND.jpg",false),
            new Player ("Maathijs","de Light",6.80,"DELIGHT.jpg",false),
            new Player ("Kalidou","Koulibaly",6.93,"KOULIBALY.jpg",false),
            new Player ("Pape","Abdou Cisse",6.55,"ABDOUCISSE.jpg",false),
            new Player ("Abdou","Diallo",6.48,"DIALLO.jpg",false),
            new Player ("Harry","Maguire",7.34,"MAGUIRE.jpg",false),
            new Player ("John","Stones",7.04,"DIER.jpg",false),
            new Player ("Sergino","Dest",6.50,"DEST.jpg",false),
            new Player ("Shaq","Moore", 6.65,"SHAQMOORE.jpg",false),
            new Player ("Ben","Davies",6.73,"BENDAVIES.jpg",false),
            new Player ("Joe","Rondon",6.63,"JOERONDON.jpg",false),
            new Player ("Nicolas","Otamendi",6.96,"OTAMENDI.jpg",false),            
            new Player ("Nicolas","Tagliafico",6.63,"TAGLIAFICO.jpg",false),
            new Player ("Gonzalo","Montiel",6.50,"MONTIEL.jpg",false),
            new Player ("Hector","Moreno",6.50,"HECTORMORENO.jpg",false),
            new Player ("Nestor","Araujo",6.30,"ARAUJO.jpg",false),
            new Player ("Jan","Bednarek",6.30,"BEDNAREK.jpg",false),
            new Player ("Kamil","Glik",6.63,"GLIK.jpg",false),
            new Player ("Simon","Kjaer",7.30,"KJAER.jpg",false),
            new Player ("Dani","Alves",6.75,"ALVES.jpg",false),
            new Player ("Thiago","Silva",7.30,"THIAGOSILVA.jpg",false),
            new Player ("Alexander","Bah",6.90,"BAH.jpg",false),
            new Player ("Theo","Henrnandez",7.30,"THEOHERNANDEZ.jpg",false),
            new Player ("Raphael","Varane",6.75,"VARANE.jpg",false),
            new Player ("Benjamin","Pavard",6.70,"PAVARD.jpg",false),
            new Player ("Ibrahima","Konate",7.32,"KONATE.jpg",false),
            new Player ("Ali","Abdi", 6.67,"ABDI.jpg",false),
            new Player ("Juan Pablo","Vargas",6.80,"PABLOVARGAS.jpg",false),
            new Player ("Niklas","Sule",6.87,"SULE.jpg",false),
            new Player ("Antonio"," Rudiger",6.77,"RUDIGERi.jpg",false),
            new Player ("Ruben","Dias",6.65,"RUBENDIAS.jpg",false),
            new Player ("Diego","Godin",7.00,"GODIN.jpg",false),
            new Player ("Takehiro","Tomiyasu",6.63,"TOMIYASU.jpg",false),
            new Player ("Yuto","Nagatomo",6.55,"NAGATOMO.jpg",false),
            new Player ("Dani","Carvajal",6.60,"CARVAJAL.jpg",false),
            new Player ("JORDI","ALBA",7.38,"ALBA.jpg",false),
            new Player ("Jan","Vertonghen",7.10,"VERTONGHEN.jpg",false),
            new Player ("Alphonso","Davies",6.60,"DAVIES.jpg",false),
            new Player ("Josko","Gvardiol",7.16,"GVARDIOL.jpg",false),
            new Player ("Josip","Juranovic",7.10,"JURANOVIC.jpg",false),
            new Player ("Dejan","Lovren",7.22,"LOVREN.jpg",false),
            new Player ("Achraf","Hakimi",6.93,"HAIKIMI.jpg",false),            
            new Player ("Badr","Benoun",6.50,"BENOUN.jpg",false),
            new Player ("Alex","Sandro",7.20,"ALEXSANDRO.jpg",false),
            new Player ("Christoper","Wooh",7.00,"WOOH.jpg",false),
            new Player ("Ricardo","Rodriguez",6.50,"RICARDORODRIGUEZ.jpg",false),
            new Player ("Gideon","Mensah",6.90,"MENSAH.jpg",false),
            new Player ("Diogo","Dalot",7.47,"DALOT.jpg",false),
            new Player ("Joao","Cancelo",6.98,"CACNELO.jpg",false)
          
           
    
    );
    
     public static ObservableList<Player> MIDFIELDERS= FXCollections.observableArrayList(
    
            new Player ("Jeremy","Sarmiento",6.67,"SARMIENTO.jpg",false),
            new Player ("Frenkie","de Jong",7.26,"FRENKIEDEJONG.jpg",false),
            new Player ("Declan","Rice",6.96,"RICE.jpg",false),
            new Player ("Jude","Bellingham",7.34,"BELLINGHAM.jpg",false),
            new Player ("Jack","Grealish",7.08,"GREALISH.jpg",false),
            new Player ("Weston","McKennie",6.70,"MCKENNIE.jpg",false),
            new Player ("Kellyn","Acosta",6.65,"ACOSTA.jpg",false),
            new Player ("Joe","Allen",6.40,"JOEALLEN.jpg",false),
            new Player ("Harry","Wilson",7.03,"HARRYWILSON.jpg",false),
            new Player ("Alexis","Mac Allister",7.0,"MACALLISTER.jpg",false),
            new Player ("Rodrigo","de Paul",6.84,"DEPAUL.jpg",false),
            new Player ("Hector","Herrera",6.70,"HERRERA.jpg",false),
            new Player ("Christian","Eriksen",7.27,"ERIKSEN.jpg",false),
            new Player ("Eduardo","Camavinga",7.55,"CAMAVINGA.jpg",false),            
            new Player ("Adrien","Rabiot",7.13,"RABIOT.jpg",false),
            new Player ("Ilkay","Gundogan",7.20,"GUNDOGAN.jpg",false),
            new Player ("Leon","Goretzka",6.57,"GORETZKA.jpg",false),
            new Player ("Thomas","Muller",6.80,"MULLER.jpg",false),
            new Player ("Takumi","Minamino",6.47,"MINAMINO.jpg",false),
            new Player ("Sergio","Busquets",6.80,"BUSQUETS.jpg",false),
            new Player ("Kevin","De Bruyne",7.20,"DEBRUYNE.jpg",false),
            new Player ("Alex","Witsel",6.93,"WISTEL.jpg",false),
            new Player ("Bruno","Fernandes",8.38,"BRUNOFERNANDES.jpg",false),
            new Player ("Heung-Min","Son",6.68,"SON.jpg",false),
            new Player ("Frederico","Valverde",7.37,"VALVERDE.jpg",false),
            new Player ("Liam","Fraser",6.50,"FRASER.jpg",false),
            new Player ("Luka","Modric",7.24,"MODRIC.jpg",false),
            new Player ("Mateo","Kovacic",7.13,"KOVACIC.jpg",false),
            new Player ("Lovro","Majer",6.78,"MAJER.jpg",false),
            new Player ("Casemiro","",7.50,"CASEMIRO.jpg",false),
            new Player ("Fabinho", "" ,7.00,"FABINHO.jpg",false),
            new Player ("Nemanja","Gudelj",6.45,"GUDELJ.jpg",false),
            new Player ("Filip","Kostic",6.75,"KOSTIC.jpg",false),
            new Player ("Xherdan","Shaqiri",6.90,"SHAQIRI.jpg",false),
            new Player ("Granit","Xhaka",6.93,"XHAKA.jpg",false),
            new Player ("Andre","Ayew",6.47,"AYEW.jpg",false),
            new Player ("Ruben","Neves",6.62,"NEVES.jpg",false),
            new Player ("Bernardo","Silva",6.66,"BERNARDOSILVA.jpg",false),
            new Player ("Sofyan","Amrabat",6.83,"AMBRAT.jpg",false)
                    
    
    );
      public static ObservableList<Player> ATTACKERS= FXCollections.observableArrayList(
            new Player ("Gabriel","Jesus",6.87,"JESUS.jpg",false),
            new Player ("Neymar","Jr",7.1,"NEYMAR.jpg",false),
            new Player ("Vinicius","Junior",7.0,"VINICIUS.jpg",false),
            new Player ("Vincent","Aboubakar",7.40,"ABOUKBAKAR.jpg",false),
            new Player ("Dusan","Tadic",7.40,"TADIC.jpg",false),
            new Player ("Dusan","Vlahovic",6.40,"VLAHOVIC.jpg",false),     
            new Player ("Cristiano","Ronaldo",6.46,"RONALDO.jpg",false),
            new Player ("Joao","Felix",7.03,"FELIX.jpg",false),
            new Player ("Edison","Cavani",6.90,"CAVANI.jpg",false),
            new Player ("Luis","Suarez",6.70,"SUAREZ.jpg",false) ,     
            new Player ("Gareth","Bale",6.80,"BALE.jpg",false),
            new Player ("Lionel","Messi",8.27,"MESSI.jpg",false),
            new Player ("Angel","Di Maria",7.18,"DIMARIA.jpg",false),
            new Player ("Lautaro","Martinez",6.35,"MARTINEZ.jpg",false),                
            new Player ("Ivan","Perisic",7.20,"PERISIC.jpg",false),
            new Player ("Marko","Livaja",6.65,"LIVAJA.jpg",false),
            new Player ("Mislav","Orsic",7.08,"ORSIC.jpg",false),
            new Player ("Robert","Lewandowski",7.05,"LEWANDOWSKI.jpg",false),            
            new Player ("Kylian","Mbappe",7.61,"MBAPPE.jpg",false),        
            new Player ("Ousmane","Dembele",6.84,"DEMBELE.jpg",false),
            new Player ("Olivier","Giroud",7.22,"GIROUD.jpg",false),
            new Player ("Kai","Havertz",7.75,"HAVERTZ.jpg",false),
            new Player ("Takuma","Asano",6.80,"ASANO.jpg",false),
            new Player ("Dani","Olmo",7.05,"OLMO.jpg",false),
            new Player ("Ferran","Torres",6.73,"TORRES.jpg",false),
            new Player ("Harry","Kane",7.22,"KANE.jpg",false),
            new Player ("Marcus","Rashford",7.10,"RASHFORD.jpg",false),
            new Player ("Romelu","Lukaku",5.95,"LUKAKU.jpg",false),
            new Player ("Eden","Hazard",6.60,"HAZARD.jpg",false),
            new Player ("Hakim","Ziyech",6.91,"ZIYECH.jpg",false),
            new Player ("Enner","Valencia",7.37,"VALENCIA.jpg",false),
            new Player ("Memphis","Depay",6.64,"DEPAY.jpg",false),
            new Player ("Christian","Pulisic",7.20,"PULISIC.jpg",false),
            new Player ("Arkadiusz","Milik",6.43,"MILIK.jpg",false)
            
    
    );
    
}

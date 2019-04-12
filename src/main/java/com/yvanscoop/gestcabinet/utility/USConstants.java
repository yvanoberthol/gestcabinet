package com.yvanscoop.gestcabinet.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class USConstants {

    public final static String US = "US";

    public final static Map<String, String> mapOfUSStates = new HashMap<String, String>() {
        {
            put("AL", "Alabama");
            put("AK", "Alaska");
            put("AZ", "Arizona");
            put("AR", "Arkansas");
            put("CA", "California");
            put("CO", "Colorado");
            put("CT", "Connecticut");
            put("DE", "Delaware");
            put("DC", "Dist of Columbia");
            put("FL", "Florida");
            put("GA", "Georgia");
            put("HI", "Hawaii");
            put("ID", "Idaho");
            put("IL", "Illinois");
            put("IN", "Indiana");
            put("IA", "Iowa");
            put("KS", "Kansas");
            put("KY", "Kentucky");
            put("LA", "Louisiana");
            put("ME", "Maine");
            put("MD", "Maryland");
            put("MA", "Massachusetts");
            put("MI", "Michigan");
            put("MN", "Minnesota");
            put("MS", "Mississippi");
            put("MO", "Missouri");
            put("MT", "Montana");
            put("NE", "Nebraska");
            put("NV", "Nevada");
            put("NH", "New Hampshire");
            put("NJ", "New Jersey");
            put("NM", "New Mexico");
            put("NY", "New York");
            put("NC", "North Carolina");
            put("ND", "North Dakota");
            put("OH", "Ohio");
            put("OK", "Oklahoma");
            put("OR", "Oregon");
            put("PA", "Pennsylvania");
            put("RI", "Rhode Island");
            put("SC", "South Carolina");
            put("SD", "South Dakota");
            put("TN", "Tennessee");
            put("TX", "Texas");
            put("UT", "Utah");
            put("VT", "Vermont");
            put("VA", "Virginia");
            put("WA", "Washington");
            put("WV", "West Virginia");
            put("WI", "Wisconsin");
            put("WY", "Wyoming");
        }
    };


    public final static List<String> listOfUSStatesCode = new ArrayList<>(mapOfUSStates.keySet());
    public final static List<String> listOfUSStatesName = new ArrayList<>(mapOfUSStates.values());

    public final static Map<String, String> mapOfWorldCountries = new HashMap<String, String>() {
        {
            put("France", "France");
            put("Afghanistan", "Afghanistan");
            put("Afrique_Centrale", "Afrique_Centrale");
            put("Afrique_du_sud", "Afrique_du_Sud");
            put("Albanie", "Albanie");
            put("Algerie", "Algerie");
            put("Allemagne", "Allemagne");
            put("Andorre", "Andorre");
            put("Angola", "Angola");
            put("Anguilla", "Anguilla");
            put("Arabie_Saoudite", "Arabie_Saoudite");
            put("Argentine", "Argentine");
            put("Armenie", "Armenie");
            put("Australie", "Australie");
            put("Autriche", "Autriche");
            put("Azerbaidjan", "Azerbaidjan");

            put("Bahamas", "Bahamas");
            put("Bangladesh", "Bangladesh");
            put("Barbade", "Barbade");
            put("Bahrein", "Bahrein");
            put("Belgique", "Belgique");
            put("Belize", "Belize");
            put("Benin", "Benin");
            put("Bermudes", "Bermudes");
            put("Bielorussie", "Bielorussie");
            put("Bolivie", "Bolivie");
            put("Botswana", "Botswana");
            put("Bhoutan", "Bhoutan");
            put("Boznie_Herzegovine", "Boznie_Herzegovine");
            put("Bresil", "Bresil");
            put("Brunei", "Brunei");
            put("Bulgarie", "Bulgarie");
            put("Burkina_Faso", "Burkina_Faso");
            put("Burundi", "Burundi");

            put("Caiman", "Caiman");
            put("Cambodge", "Cambodge");
            put("Cameroun", "Cameroun");
            put("Canada", "Canada");
            put("Canaries", "Canaries");
            put("Cap_vert", "Cap_Vert");
            put("Chili", "Chili");
            put("Chine", "Chine");
            put("Chypre", "Chypre");
            put("Colombie", "Colombie");
            put("Comores", "Colombie");
            put("Congo", "Congo");
            put("Congo_democratique", "Congo_democratique");
            put("Cook", "Cook");
            put("Coree_du_Nord", "Coree_du_Nord");
            put("Coree_du_Sud", "Coree_du_Sud");
            put("Costa_Rica", "Costa_Rica");
            put("Cote_d_Ivoire", "Côte_d_Ivoire");
            put("Croatie", "Croatie");
            put("Cuba", "Cuba");

            put("Danemark", "Danemark");
            put("Djibouti", "Djibouti");
            put("Dominique", "Dominique");

            put("Egypte", "Egypte");
            put("Emirats_Arabes_Unis", "Emirats_Arabes_Unis");
            put("Equateur", "Equateur");
            put("Erythree", "Erythree");
            put("Espagne", "Espagne");
            put("Estonie", "Estonie");
            put("Etats_Unis", "Etats_Unis");
            put("Ethiopie", "Ethiopie");

            put("Falkland", "Falkland");
            put("Feroe", "Feroe");
            put("Fidji", "Fidji");
            put("Finlande", "Finlande");
            put("France", "France");

            put("Gabon", "Gabon");
            put("Gambie", "Gambie");
            put("Georgie", "Georgie");
            put("Ghana", "Ghana");
            put("Gibraltar", "Gibraltar");
            put("Grece", "Grece");
            put("Grenade", "Grenade");
            put("Groenland", "Groenland");
            put("Guadeloupe", "Guadeloupe");
            put("Guam", "Guam");
            put("Guatemala", "Guatemala");
            put("Guernesey", "Guernesey");
            put("Guinee", "Guinee");
            put("Guinee_Bissau", "Guinee_Bissau");
            put("Guinee equatoriale", "Guinee_Equatoriale");
            put("Guyana", "Guyana");
            put("Guyane_Francaise", "Guyane_Francaise");

            put("Haiti", "Haiti");
            put("Hawaii", "Hawaii");
            put("Honduras", "Honduras");
            put("Hong_Kong", "Hong_Kong");
            put("Hongrie", "Hongrie");

            put("Inde", "Inde");
            put("Indonesie", "Indonesie");
            put("Iran", "Iran");
            put("Iraq", "Iraq");
            put("Irlande", "Irlande");
            put("Islande", "Islande");
            put("Israel", "Israel");
            put("Italie", "italie");

            put("Jamaique", "Jamaique");
            put("Jan Mayen", "Jan Mayen");
            put("Japon", "Japon");
            put("Jersey", "Jersey");
            put("Jordanie", "Jordanie");

            put("Kazakhstan", "Kazakhstan");
            put("Kenya", "Kenya");
            put("Kirghizstan", "Kirghizistan");
            put("Kiribati", "Kiribati");
            put("Koweit", "Koweit");

            put("Laos", "Laos");
            put("Lesotho", "Lesotho");
            put("Lettonie", "Lettonie");
            put("Liban", "Liban");
            put("Liberia", "Liberia");
            put("Liechtenstein", "Liechtenstein");
            put("Lituanie", "Lituanie");
            put("Luxembourg", "Luxembourg");
            put("Lybie", "Lybie");

            put("Macao", "Macao");
            put("Macedoine", "Macedoine");
            put("Madagascar", "Madagascar");
            put("Madère", "Madère");
            put("Malaisie", "Malaisie");
            put("Malawi", "Malawi");
            put("Maldives", "Maldives");
            put("Mali", "Mali");
            put("Malte", "Malte");
            put("Man", "Man");
            put("Mariannes du Nord", "Mariannes du Nord");
            put("Maroc", "Maroc");
            put("Marshall", "Marshall");
            put("Martinique", "Martinique");
            put("Maurice", "Maurice");
            put("Mauritanie", "Mauritanie");
            put("Mayotte", "Mayotte");
            put("Mexique", "Mexique");
            put("Micronesie", "Micronesie");
            put("Midway", "Midway");
            put("Moldavie", "Moldavie");
            put("Monaco", "Monaco");
            put("Mongolie", "Mongolie");
            put("Montserrat", "Montserrat");
            put("Mozambique", "Mozambique");

            put("Namibie", "Namibie");
            put("Nauru", "Nauru");
            put("Nepal", "Nepal");
            put("Nicaragua", "Nicaragua");
            put("Niger", "Niger");
            put("Nigeria", "Nigeria");
            put("Niue", "Niue");
            put("Norfolk", "Norfolk");
            put("Norvege", "Norvege");
            put("Nouvelle_Caledonie", "Nouvelle_Caledonie");
            put("Nouvelle_Zelande", "Nouvelle_Zelande");

            put("Oman", "Oman");
            put("Ouganda", "Ouganda");
            put("Ouzbekistan", "Ouzbekistan");

            put("Pakistan", "Pakistan");
            put("Palau", "Palau");
            put("Palestine", "Palestine");
            put("Panama", "Panama");
            put("Papouasie_Nouvelle_Guinee", "Papouasie_Nouvelle_Guinee");
            put("Paraguay", "Paraguay");
            put("Pays_Bas", "Pays_Bas");
            put("Perou", "Perou");
            put("Philippines", "Philippines");
            put("Pologne", "Pologne");
            put("Polynesie", "Polynesie");
            put("Porto_Rico", "Porto_Rico");
            put("Portugal", "Portugal");

            put("Qatar", "Qatar");

            put("Republique_Dominicaine", "Republique_Dominicaine");
            put("Republique_Tcheque", "Republique_Tcheque");
            put("Reunion", "Reunion");
            put("Roumanie", "Roumanie");
            put("Royaume_Uni", "Royaume_Uni");
            put("Russie", "Russie");
            put("Rwanda", "Rwanda");

            put("Sahara Occidental", "Sahara Occidental");
            put("Sainte_Lucie", "Sainte_Lucie");
            put("Saint_Marin", "Saint_Marin");
            put("Salomon", "Salomon");
            put("Salvador", "Salvador");
            put("Samoa_Occidentales", "Samoa_Occidentales");
            put("Samoa_Americaine", "Samoa_Americaine");
            put("Sao_Tome_et_Principe", "Sao_Tome_et_Principe");
            put("Senegal", "Senegal");
            put("Seychelles", "Seychelles");
            put("Sierra Leone", "Sierra Leone");
            put("Singapour", "Singapour");
            put("Slovaquie", "Slovaquie");
            put("Slovenie", "Slovenie");
            put("Somalie", "Somalie");
            put("Soudan", "Soudan");
            put("Sri_Lanka", "Sri_Lanka");
            put("Suede", "Suede");
            put("Suisse", "Suisse");
            put("Surinam", "Surinam");
            put("Swaziland", "Swaziland");
            put("Syrie", "Syrie");

            put("Tadjikistan", "Tadjikistan");
            put("Taiwan", "Taiwan");
            put("Tonga", "Tonga");
            put("Tanzanie", "Tanzanie");
            put("Tchad", "Tchad");
            put("Thailande", "Thailande");
            put("Tibet", "Tibet");
            put("Timor_Oriental", "Timor_Oriental");
            put("Togo", "Togo");
            put("Trinite_et_Tobago", "Trinite_et_Tobago");
            put("Tristan da cunha", "Tristan de cuncha");
            put("Tunisie", "Tunisie");
            put("Turkmenistan", "Turmenistan");
            put("Turquie", "Turquie");

            put("Ukraine", "Ukraine");
            put("Uruguay", "Uruguay");

            put("Vanuatu", "Vanuatu");
            put("Vatican", "Vatican");
            put("Venezuela", "Venezuela");
            put("Vierges_Americaines", "Vierges_Americaines");
            put("Vierges_Britanniques", "Vierges_Britanniques");
            put("Vietnam", "Vietnam");

            put("Wake", "Wake");
            put("Wallis et Futuma", "Wallis et Futuma");

            put("Yemen", "Yemen");
            put("Yougoslavie", "Yougoslavie");

            put("Zambie", "Zambie");
            put("Zimbabwe", "Zimbabwe");
        }
    };

    public final static List<String> listOfCountryCode = new ArrayList<>(mapOfWorldCountries.keySet());
    public final static List<String> listOfCountryName = new ArrayList<>(mapOfWorldCountries.values());
}

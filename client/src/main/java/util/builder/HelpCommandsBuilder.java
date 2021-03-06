/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.builder;

import app_info.Configuration;
import util.help.Command;
import util.help.HelpCommands;
import util.help.Information;
import util.help.Parameter;

/**
 *
 * @author robert
 */
public class HelpCommandsBuilder
{
    public static HelpCommands build()
    {
        HelpCommands helpCommands = new HelpCommands();
        String commandPrefix = Configuration.getCommandPrefix();
        
        // register command
        String name = "register";
        String shortDescription = "Umożliwia rejestrację użytkownika na "
                + "serwerze";
        String description = shortDescription + ".\nHasło powinno składać się "
                + "z 4-12 znaków";
        Parameter[] parameters = new Parameter[]
        {
            new Parameter("username", "nazwa użytkownika, 4-8 liter, cyfr lub"
                    + " znaków podkreślenia"),
        };
        Command command = new Command(name, description, shortDescription, 
                parameters);
        helpCommands.addCommand(name, command);
        
        // login command
        name = "login";
        shortDescription = "Umożliwia zalogowanie użytkownika na serwerze";
        parameters = new Parameter[]
        {
            new Parameter("username", "nazwa użytkownika, 4-8 liter, cyfr lub"
                    + " znaków podkreślenia"),
        };
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // help command
        name = "help";
        shortDescription = "Wyświetla szczegółową pomoc o podanej komendzie";
        description = shortDescription + ".\nWpisanie bez parametru wyświetla "
                + "informacje o wszystkich komendach i informacjach";
        parameters = new Parameter[]
        {
            new Parameter("name", "komenda dla której ma zostać wyświetlona"
                    + " szczegółowa pomoc")
        };
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // calc command
        name = "calc";
        shortDescription = "Pozwala wykonać proste obliczenia arytmetyczne na"
                + " dwóch liczbach całkowitych";
        description = "equation jest w postaci: [liczba1][operand][liczba2]\n" +
                "Liczby są liczbami całkowitymi typu int (32 bit)\n" +
                "Jako operand mozemy wpisać:\n" + 
                "  + dodawanie\n" + 
                "  - odejmowanie\n" + 
                "  * mnozenie\n" + 
                "  / dzielenie\n" + 
                "  ^ potęgowanie\n\n" +
                "Przykładowe wywołania:\n" +
                commandPrefix + "calc" + " 10+20\n" + 
                commandPrefix + "calc" + " -15*23\n" + 
                commandPrefix + "calc" + " 5^-2";
        parameters = new Parameter[]
        {
            new Parameter("equation", "działanie arytmetyczne, które chcemy "
                    + "obliczyć")
        };
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // connect command
        name = "connect";
        shortDescription = "Łączy z serwerem";
        description = "";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // disconnect command
        name = "disconnect";
        shortDescription = "Rozłącza z serwerem";
        description = "";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // logout command
        name = "logout";
        shortDescription = "Wylogowuje użytkownika z serwera";
        description = "";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // users list
        name = "users";
        shortDescription = "Wyświetla listę zalogowanych użytkowników";
        description = "";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // blacklist
        name = "blacklist";
        shortDescription = "Wyświetla czarną listę użytkownika";
        description = "Czarna lista pozwala na wpisywanie użytkowników z "
                + "którymi nie chcemy rozmawiać.\nJeżeli użytkownik wpisany "
                + "na czarną listę będzie chciał z nami rozmawiać "
                + "dostanie informację, że jesteśmy zajęci.\n" + 
                "Aby wpisać użytkownika na czarną listę użyj komendy " + 
                commandPrefix + "block\n" + 
                "Aby usunąć użytkownika z czarnej listy użyj komendy " +
                commandPrefix + "unblock\n";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // block
        name = "block";
        shortDescription = "Wpisuje użytkownika o podanym loginie na czarną "
                + "listę";
        description = "";
        parameters = new Parameter[]
        {
            new Parameter("username", "nazwa użytkownika, którego chcemy "
                    + "dodać na czarną listę")
        };
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // unblock
        name = "unblock";
        shortDescription = "Usuwa użytkownika o podanym loginie z czarnej "
                + "listy";
        description = "";
        parameters = new Parameter[]
        {
            new Parameter("username", "nazwa użytkownika, którego chcemy "
                    + "usunąć z czarnej listy")
        };
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // state command
        name = "state";
        shortDescription = "Pokazuje aktualny stan aplikacji";
        description = "Możliwe stany aplikacji to:\n" +
                "Niepołączony - aplikacja nie jest połączona z serwerem\n" + 
                "Połączony - aplikacja jest połączona z serwerem\n" +
                "Zalogowany jako [username] - użytkownik jest zalogowany na "
                + "serwerze\n" +
                "Konwersacja [username] z [username] - obecnie prowadzona jest"
                + " konwersacja";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // talk command
        name = "talk";
        shortDescription = "Próbuje nawiązać konwersację z użytkownikiem o "
                + "podanym loginie";
        description = "Nawiązuje rozmowę z innym użytkownikiem jeżeli:\n" +
                "-jest zalogowany\n" + 
                "-nie rozmawia z nikim innym\n" + 
                "-nie jesteśmy wpisani na jego czarną listę\n" + 
                "-nie wpisaliśmy go na swoją czarną listę";
        parameters = new Parameter[]
        {
            new Parameter("username", "nazwa użytkownika z którym chcemy "
                    + "nawiązać konwersację")
        };
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // endtalk command
        name = "endtalk";
        shortDescription = "Zakańcza konwersację";
        description = "";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
    
        // weather
        name = "weather";
        shortDescription = "Wyświetla pogodę i prognozę dla podanego miasta";
        description = "Pogoda jest pobierana z serwisu Yahoo! Weather.\n" + 
                "Można wpisywać zarówno polskie jak i angielskie nazwy "
                + "miast, w przypadku gdy pasować będzie więcej niż jedno "
                + "zostanie\nzwrócona pogoda dla pierwszego pasującego";
        parameters = new Parameter[]
        {
            new Parameter("town_name", "Nazwa miasta, dla którego chcemy "
                    + "wyświetlić pogodę. Może być kilku członowa oddzielona "
                    + "spacjami")
        };
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // mscreen
        name = "mscreen";
        shortDescription = "Powraca do głównego ekranu dla danego stanu "
                + "aplikacji";
        description = "Kazdy stan aplikacji ma swój ekran główny do którego "
                + "można wrócić w każdym momencie dzięki tej komendzie.\n" + 
                "Przykładowo jeżeli podczas konwersacji weszliśmy do helpa, "
                + "możemy do niej wrócić poprzez wpisanie tej komendy";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // config
        name = "config";
        shortDescription = "Konfiguruje adres i port serwera na których "
                + "będziemy próbowali się z nim połączyć";
        description = "Ustawia nowy adres i port serwera dla bieżącej "
                + "sesji aplikacji i zapisuje je do pliku konfiguracyjnego";
        parameters = new Parameter[]
        {
            new Parameter("address", "adres IPv4 serwera"),
            new Parameter("port", "port serwera")
        };
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // exit
        name = "exit";
        shortDescription = "Zakańcza działanie aplikacji";
        description = "";
        parameters = new Parameter[0];
        command = new Command(name, description, shortDescription, parameters);
        helpCommands.addCommand(name, command);
        
        // authors information
        name = "authors";
        shortDescription = "Wyświetla informację o autorach aplikacji";
        description = "Aplikacja enChat została napisana przez zespół "
                + "io_fighters jako projekt z inżynierii oprogramowania\n\n" + 
                "Członkowie zespołu:\n" +
                "kierownik projektu: tBajorek <tb@tbajorek.pl>\n" +
                "programista/inżynier: bercik <robert.cebula1@gmail.com>\n" +
                "programista: Mati <matello455@gmail.com>";
        
        Information information = new Information(name, description, 
                shortDescription);
        helpCommands.addInformation(name, information);
        // keys information
        name = "keys";
        shortDescription = "Wyświetla informacje o klawiszach specjalnych";
        description = "Klawisze specjalne w aplikacji enChat:\n" + 
                "ESCAPE - wychodzi z komendy logowania i rejestracji\n" +
                "DELETE - usuwa całą linię komend (działa także podczas "
                + "wpisywania hasła)\n" + 
                "STRZAŁKI GÓRA/DÓŁ - umożliwia przeglądanie historii "
                + "wpisywanych komend\n" + 
                "TAB - autouzupełnianie komend i helpa";
        information = new Information(name, description, shortDescription);
        helpCommands.addInformation(name, information);
        
        return helpCommands;
    }
}

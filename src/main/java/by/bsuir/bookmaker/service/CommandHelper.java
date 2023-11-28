package by.bsuir.bookmaker.service;

import java.util.HashMap;
import java.util.Map;

import by.bsuir.bookmaker.service.impl.auth.RegisterCommand;
import by.bsuir.bookmaker.service.impl.auth.SignInCommand;
import by.bsuir.bookmaker.service.impl.auth.LogoutCommand;
import by.bsuir.bookmaker.service.impl.NoSuchCommand;
import by.bsuir.bookmaker.service.impl.betType.GetAllBetTypeCommand;
import by.bsuir.bookmaker.service.impl.event.GetAllEventsCommand;
import by.bsuir.bookmaker.service.impl.locale.ChangeLocaleCommand;
import by.bsuir.bookmaker.service.impl.participants.AddParticipantCommand;
import by.bsuir.bookmaker.service.impl.participants.GetAllParticipantsCommand;
import by.bsuir.bookmaker.service.impl.sportType.AddSportTypeCommand;
import by.bsuir.bookmaker.service.impl.sportType.GetAllSportTypeCommand;
import by.bsuir.bookmaker.service.impl.tournament.AddTournamentCommand;
import by.bsuir.bookmaker.service.impl.tournament.GetAllTournamentCommand;
import by.bsuir.bookmaker.service.impl.userBet.GetAllUsersBetCommand;

public final class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();
    private final Map<CommandName, ICommand> commands = new HashMap<>();
    public CommandHelper() {
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.SIGN_IN_COMMAND, new SignInCommand());
        commands.put(CommandName.LOGOUT_COMMAND, new LogoutCommand());
        commands.put(CommandName.REGISTER_COMMAND, new RegisterCommand());
        commands.put(CommandName.CHANGE_LOCALE_COMMAND, new ChangeLocaleCommand());
        commands.put(CommandName.GET_ALL_SPORT_TYPE_COMMAND, new GetAllSportTypeCommand());
        commands.put(CommandName.ADD_SPORT_TYPE_COMMAND, new AddSportTypeCommand());
        commands.put(CommandName.GET_ALL_PARTICIPANTS_COMMAND, new GetAllParticipantsCommand());
        commands.put(CommandName.ADD_PARTICIPANT_COMMAND, new AddParticipantCommand());
        commands.put(CommandName.GET_ALL_TOURNAMENTS_COMMAND, new GetAllTournamentCommand());
        commands.put(CommandName.ADD_TOURNAMENT_COMMAND, new AddTournamentCommand());
        commands.put(CommandName.GET_ALL_BET_TYPE_COMMAND, new GetAllBetTypeCommand());
        commands.put(CommandName.GET_ALL_USERS_BET_COMMAND, new GetAllUsersBetCommand());
        commands.put(CommandName.GET_ALL_EVENTS_COMMAND, new GetAllEventsCommand());
    }

    public static CommandHelper getInstance() {
        return instance;
    }
    public ICommand getCommand(String commandName){
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        return name != null ? commands.get(name) : commands.get(CommandName.NO_SUCH_COMMAND);
    }
}

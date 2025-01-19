package servlet;

import service.DefaultMatchService;
import service.DefaultPlayerService;
import service.MatchService;
import service.PlayerService;
import util.validator.PlayerNameValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final MatchService matchService = DefaultMatchService.getInstance();
    private final PlayerNameValidator validator = new PlayerNameValidator();
    private final PlayerService playerService = new DefaultPlayerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = req.getParameter("playerOne");
        String secondPlayerName = req.getParameter("playerTwo");

        validator.validate(firstPlayerName, secondPlayerName);

        playerService.createIfNotExists(firstPlayerName);
        playerService.createIfNotExists(secondPlayerName);

        Integer playerOneID = playerService.findByName(firstPlayerName).get().getId();
        Integer playerTwoID = playerService.findByName(secondPlayerName).get().getId();

        UUID matchUUID = matchService.createMatch(playerOneID, playerTwoID);

        resp.sendRedirect(req.getContextPath() + "/match-score?matchUUID=" + matchUUID);


    }
}

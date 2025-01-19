package servlet;

import dto.MatchResponseDto;
import service.MatchService;
import service.factory.MatchServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private final MatchService matchService = MatchServiceFactory.getMatchService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MatchesServlet doGet");
        List<MatchResponseDto> matches = matchService.getAll();
        req.setAttribute("matches", matches);
        req.getRequestDispatcher("/matches.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String playerName = req.getParameter("name");
    List<MatchResponseDto> matches = matchService.findMatchesByPlayerName(playerName);
    req.setAttribute("matches", matches);
    req.getRequestDispatcher("/matches.jsp").forward(req, resp);
    }
}

package servlet;

import dto.MatchResponseDto;
import entity.Match;
import entity.Player;
import repository.DefaultMatchRepository;
import repository.DefaultPlayerRepository;
import repository.MatchRepository;
import repository.PlayerRepository;
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
    private static final int PAGE_SIZE = 5;
    private final MatchService matchService = MatchServiceFactory.getMatchService();

    @Override
    public void init() throws ServletException {
        for(int i = 0; i<15; i++){
            MatchRepository matchRepository = new DefaultMatchRepository();
            PlayerRepository playerRepository = new DefaultPlayerRepository();

            Player player = new Player("i" + i);
            Player player2 = new Player(i + "i");
            playerRepository.save(player);
            playerRepository.save(player2);
            Match match = new Match();
            match.setWinner(player);
            match.setPlayer1(player);
            match.setPlayer2(player2);
            matchRepository.save(match);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MatchResponseDto> allMatches;
        String playerName = req.getParameter("name");
        int page = 1;
        String pageParam = req.getParameter("page");

        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        if(playerName!=null){
            allMatches = matchService.findMatchesByPlayerName(playerName);
        }
        else {
            allMatches = matchService.getAll();
        }
        int totalMatches = allMatches.size();
        int startIndex = (page - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalMatches);
        List<MatchResponseDto> matchesOnPage = allMatches.subList(startIndex, endIndex);

        req.setAttribute("matches", matchesOnPage);
        req.setAttribute("totalMatches", totalMatches);
        req.setAttribute("currentPage", page);
        req.setAttribute("pageSize", PAGE_SIZE);

        req.getRequestDispatcher("/matches.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.sendRedirect(req.getContextPath() + "/matches");
    }
}

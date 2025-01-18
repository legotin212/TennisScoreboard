package servlet;

import dto.ScoreResponseDto;
import service.MatchScoreService;
import service.MatchService;
import service.factory.MatchServiceFactory;
import service.model.OngoingMatch;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private MatchService matchService = MatchServiceFactory.getMatchService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getParameter("matchUUID");
        UUID matchUUID = UUID.fromString(request.getParameter("matchUUID"));///Валидация

        OngoingMatch match = matchService.getOngoingMatch(matchUUID);
        ScoreResponseDto score =  matchService.getScoreResponseDto(match);

        request.setAttribute("playerScores", score);
        request.setAttribute("matchUUID", matchUUID);


        RequestDispatcher dispatcher = request.getRequestDispatcher("match-score.jsp" );
        dispatcher.forward(request, response);
        }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchUUID = UUID.fromString(req.getParameter("matchUUID"));
        Integer playerId = Integer.parseInt(req.getParameter("playerId"));

        OngoingMatch match = matchService.getOngoingMatch(matchUUID);
        MatchScoreService matchScoreService = new MatchScoreService();
        matchScoreService.addPointToPlayer(match, playerId);

        OngoingMatch m = matchService.getOngoingMatch(matchUUID);
        ScoreResponseDto score =  matchService.getScoreResponseDto(m);

        req.setAttribute("playerScores", score);
        req.setAttribute("matchUUID", matchUUID);
        RequestDispatcher dispatcher = req.getRequestDispatcher("match-score.jsp" );
        dispatcher.forward(req, resp);
    }
}




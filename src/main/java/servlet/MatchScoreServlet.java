package servlet;

import dto.ScoreResponseDto;
import service.MatchScoreService;
import service.OngoingMatchService;
import service.factory.OngoingMatchServiceFactory;
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
    private OngoingMatchService ongoingMatchService = OngoingMatchServiceFactory.getMatchService();
    MatchScoreService matchScoreService = new MatchScoreService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getParameter("matchUUID");
        UUID matchUUID = UUID.fromString(request.getParameter("matchUUID"));

        OngoingMatch match = ongoingMatchService.getOngoingMatch(matchUUID);

        ScoreResponseDto score =  ongoingMatchService.getScoreResponseDto(match);

        request.setAttribute("playerScores", score);
        request.setAttribute("matchUUID", matchUUID);

        RequestDispatcher dispatcher = request.getRequestDispatcher("match-score.jsp" );
        dispatcher.forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchUUID = UUID.fromString(req.getParameter("matchUUID"));
        Integer playerId = Integer.parseInt(req.getParameter("playerId"));

        OngoingMatch match = ongoingMatchService.getOngoingMatch(matchUUID);
        matchScoreService.addPointToPlayer(match, playerId);

        if(match.isFinished()){
            resp.sendRedirect(req.getContextPath() + "/matches");
        }
        else {
            ScoreResponseDto score = ongoingMatchService.getScoreResponseDto(match);
            req.setAttribute("playerScores", score);
            req.setAttribute("matchUUID", matchUUID);
            RequestDispatcher dispatcher = req.getRequestDispatcher("match-score.jsp");
            dispatcher.forward(req, resp);
        }
    }
}




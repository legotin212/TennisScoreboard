package servlet;

import service.DefaultPlayerService;
import service.MatchService;
import service.PlayerService;
import service.factory.MatchServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private MatchService matchService;


    @Override
    public void init() throws ServletException {
       this.matchService= MatchServiceFactory.getMatchService();
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        request.getParameter("matchUUID ");

        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getParameter("matchUUID");
        req.getParameter("playerID");
    }
}




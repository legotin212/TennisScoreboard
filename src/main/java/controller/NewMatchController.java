package controller;

import service.DefaultMatchService;
import service.MatchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchController extends HttpServlet {
    private  MatchService matchService;
    @Override
    public void init() throws ServletException {
       matchService = DefaultMatchService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/new-match.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = req.getParameter("PlayerOne");
        String secondPlayerName = req.getParameter("PlayerTwo");
        System.out.println(firstPlayerName);
        System.out.println(secondPlayerName);
//        validator.validate(firstPlayerName,secondPlayerName);
        UUID newMatchUUID = matchService.createMatch(firstPlayerName,secondPlayerName);
        /// C помощью UUID генерировать ссылку??
        System.out.println(newMatchUUID);
        //redirect to(куда-то/UUID)
    }
}

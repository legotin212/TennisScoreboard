package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/new-match")
public class newMatchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = req.getParameter("PlayerOne");
        String secondPlayerName = req.getParameter("PlayerTwo");
//        validator.validate(firstPlayerName,secondPlayerName);
//        UUID newMatchUUID = matchService.create(firstPlayerName,secondPlayerName);
        /// C помощью UUID генерировать ссылку??
        //redirect to(куда-то/UUID)
    }
}

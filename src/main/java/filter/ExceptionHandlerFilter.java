package filter;

import exception.MatchNotFoundException;
import exception.WrongArgumentException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlerFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        try{
            chain.doFilter(req, res);
        }
        catch(WrongArgumentException e){
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("new-match.jsp").forward(req, res);
        }

        catch (MatchNotFoundException e){
            req.getRequestDispatcher("matches.jsp").forward(req, res);
        }
    }
}

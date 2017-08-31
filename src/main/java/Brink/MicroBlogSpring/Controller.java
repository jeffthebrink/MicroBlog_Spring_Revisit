package Brink.MicroBlogSpring;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@org.springframework.stereotype.Controller
public class Controller {

    static String loggedInUser = "loggedInUser";
    static ArrayList<Message> messageList = new ArrayList<>();


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        model.addAttribute("name", session.getAttribute(loggedInUser));
        model.addAttribute("messageList", session.getAttribute("messageList"));
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName){
        session.setAttribute(loggedInUser, userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/add-message", method = RequestMethod.POST)
    public String addMessage(HttpSession session, String text) {
        Message message = new Message(messageList.size() + 1, text);
        messageList.add(message);
        session.setAttribute("messageList", messageList);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String deleteMessage(HttpSession session, int id) {
        int tempId = messageList.get(id-1).id;
        messageList.remove(id-1);
        for (Message message : messageList
             ) {
            if (message.id > tempId) {
                message.id = (message.id - 1);
            }
        }

        session.setAttribute("messageList", messageList);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}

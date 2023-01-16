package nu.rolandsson.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nu.rolandsson.model.ShoppingItem;
import nu.rolandsson.model.ShoppingList;
import nu.rolandsson.service.ShoppingListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/shoppingList")
public class ShoppingListController {

  private ShoppingListService listService;

  public ShoppingListController() {
    listService = new ShoppingListService();
  }

  @GetMapping
  @ResponseBody
  protected String doGet(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
    //resp.setContentType("text/html"); hanteras responseBody
    String username = (String) session.getAttribute("username");


    ShoppingList userShoppingList = listService.getShoppingList(username);

    StringBuilder output = new StringBuilder();

    for (ShoppingItem item : userShoppingList.getItemList()) {
      output.append(item).append("<br>");
    }

    req.getRequestDispatcher("/shoppingList.jsp").include(req, resp);
    return output.toString();
  }

  @PostMapping
  public String addItem(HttpSession session, @ModelAttribute ShoppingItem shoppingItem) {
    String username = (String) session.getAttribute("username");

    listService.addShoppingItem(username, shoppingItem);

    return "redirect:/shoppingList";
  }
}

package com.controller;

import com.pojo.Books;
import com.service.BookService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialStruct;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;


    //查询全部数据并返回
    @RequestMapping("/BookOp")
    public String list(Model model){
        List<Books> list = bookService.queryAllBook();
        model.addAttribute("list",list);
        return "allBook";
    }

    @RequestMapping("/toAddBook")
    public String toAddBook(){
        return "addBook";
    }

    @RequestMapping("/addBookOp")
    public String addBookOp(Books books){
        System.out.println(books);
        bookService.addBook(books);
        return "redirect:/book/BookOp";

    }

    @RequestMapping("/toUpdateBook")
    public String toUpdateBook(int id,Model model){
        Books books = bookService.queryBookById(id);
        model.addAttribute("Qbook",books);
        return "updateBook";
    }

    @RequestMapping("/updateBookOp")
    public String updateBookOp(Model model , Books books){
        System.out.println("UpdataBook"+books);
        bookService.updateBook(books);

        return "redirect:/book/BookOp";
    }

    @RequestMapping("/deleteBookOp/{bookid}")//reustful风格而已
    public String deleteBookOp(@PathVariable("bookid") int id){
        bookService.deleteBookById(id);
        return "redirect:/book/BookOp";

    }
    @RequestMapping("/queryBookNameOp")
    public String queryBookNameOp(String queryBookName ,Model model){
        String queryBookNameLike = "%"+ queryBookName+ "%";
        List<Books> list = bookService.queryBookName(queryBookNameLike);
        model.addAttribute("list",list);
        return "allBook";
    }

    @RequestMapping("/goOut")
    @ResponseBody
    public void goOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.jsp");
    }

}

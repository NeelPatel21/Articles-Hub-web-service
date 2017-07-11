/*
 * The MIT License
 *
 * Copyright 2017 Neel Patel.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hibernate;

import com.articles_hub.model.CommentDetail;
import com.articles_hub.service.CommentService;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author Neel Patel
 */
public class CommentServiceTest {
    private static CommentService us;
    public static void main(String... arg){
        System.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/Articles_Hub");
        us=CommentService.getCommentService();
//        addUsers(1,15);
//        getUserbyName();
//        getUserbyId();
//        getArticlebyId();
//        addArticles(4);
//        getArticleByTag();
//        addTag(2);
        addComments(4);
//        addlike(10);
        System.exit(0);
    }
    
//    public static void addUsers(int x,int y){
//        for(int i=x;i<y;i++){
//            UserDetailWithPass ud=new UserDetailWithPass();
//            ud.setUserName("user_"+i);
//            ud.setInfo("u.s.e.r. "+(i*x)+" "+(i*24)+" "+(x*24));
//            ud.setPass("pass"+i);
//            ud.setEmailId("user"+i+"@gmail.com");
//            System.out.println("state "+us.addUser(ud));
//            System.out.println("user - "+us.getUserDetail(ud.getUserName()));
//        }
//    }
    
//    public static void getUserbyName(){
//        Scanner sc=new Scanner(System.in);
//        String name="";
//        System.out.println("user name ?");
//        for(name=sc.nextLine();name.trim().equals("");name=sc.nextLine());
//        UserDetail ud=us.getUserDetail(name);
//        System.out.println("Test name - "+ud.getUserName());
//        System.out.println("Test pass - "+ud.getPass());
//        System.out.println("Test email - "+ud.getEmailId());
//        System.out.println("Test info - "+ud.getInfo());
//        System.out.println("Test article - "+ud.getArticles());
//        System.out.println("Test comment - "+ud.getComments());
//        System.out.println("Test likes - "+ud.getLikes());
//    }
//    
//    public static void getUserbyId(){
//        Scanner sc=new Scanner(System.in);
//        System.out.println("user id ?");
//        long id=sc.nextLong();
//        UserProfile up=db.getUserProfile(id);
//        System.out.println("Test name - "+up.getUserName());
//        System.out.println("Test pass - "+up.getPass());
//        System.out.println("Test email - "+up.getEmailId());
//        System.out.println("Test info - "+up.getInfo());
//        System.out.println("Test article - "+up.getArticles());
//        System.out.println("Test comment - "+up.getComments());
//        System.out.println("Test likes - "+up.getLikes());
//    }
//    
//    public static void addArticles(int x){
//        Scanner sc=new Scanner(System.in);
//        for(int i=0;i<x;i++){
//            ArticleDetail ar=new ArticleDetail();
//            ar.setTitle("article "+i);
//            ar.setDate(LocalDateTime.now());
//            List<String> li=new ArrayList<>();
//            for(int j=0;j<i*x;j++)
//                li.add("line "+j+" "+i+" "+x);
//            ar.setContant(li);
//            System.out.println("user name ?");
//            String name="";
//            for(name=sc.nextLine();name.trim().equals("");name=sc.nextLine());
//            ar.setAuthor(name);
//            System.out.println("tag name ?");
//            name="";
//            for(name=sc.nextLine();name.trim().equals("");name=sc.nextLine());
//            Set<String> tags=new HashSet<>();
//            tags.add(name);
//            ar.setTag(tags);
//            System.out.println("state - "+us.addArticle(ar));
////            System.out.println("article - "+us.getArticleDetail(i));
//        }
//    }
//    
//    public static void getArticlebyId(){
//        Scanner sc=new Scanner(System.in);
//        System.out.println("article id ?");
//        long id=sc.nextLong();
//        ArticleDetail ar=us.getArticleDetail(id);
//        System.out.println("Test title - "+ar.getTitle());
//        System.out.println("Test id - "+ar.getArticleId());
//        System.out.println("Test author - "+ar.getAuthor());
//        System.out.println("Test date - "+ar.getDate());
//        System.out.println("Test data - "+ar.getContant());
////        System.out.println("Test comment - "+ar.getComments());
////        System.out.println("Test likes - "+ar.getLikes());
//        System.out.println("Test tags - "+ar.getTag());
//    }
//    
//    public static void addTag(int x){
//        Scanner sc=new Scanner(System.in);
//        for(int i=0;i<x;i++){
//            Tag t=new Tag();
//            System.out.println("tag name ?");
//            String name="";
//            for(name=sc.nextLine();name.trim().equals("");name=sc.nextLine());
//            t.setTagName(name);
//            System.out.println("tag id - "+db.storeTag(t));
//        }
//    }
//    
    public static void addComments(int x){
        Scanner sc=new Scanner(System.in);
        for(int i=0;i<x;i++){
            CommentDetail ar=new CommentDetail();
            ar.setContant("line "+i+" "+x);
            ar.setDate(LocalDateTime.now());
            System.out.println("article id ?");
            long tid=sc.nextLong();
            ar.setArticleId(tid);
            System.out.println("user name ?");
            String name="";
            for(name=sc.nextLine();name.trim().equals("");name=sc.nextLine());
            ar.setUserName(name);
            us.addComment(ar);
        }
    }
//    
//    public static void addlike(int x){
//        Scanner sc=new Scanner(System.in);
//        for(int i=0;i<x;i++){
//            System.out.println("user id ");
//            long uid=sc.nextLong();
//            System.out.println("article id ");
//            long tid=sc.nextLong();
//            db.getArticle(tid).addLike(db.getUserProfile(uid));
//        }
//    }
//    
//    public static void getArticleByTag(){
//        Scanner sc=new Scanner(System.in);
//        System.out.println("tag id ?");
//        long id=sc.nextLong();
//        db.getArticles(db.getTag(id)).forEach(x->System.out.println("test - "+x));
//        
//    }
}

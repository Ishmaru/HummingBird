package com.tts.TechTalentTwitter.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.TweetService;
import com.tts.TechTalentTwitter.service.UserService;

@Controller
public class TweetController {
	
    @Autowired
    private UserService userService;
	
    @Autowired
    private TweetService tweetService;
    
    @GetMapping(value= {"/tweets", "/"})
    public String getTweets(Model model){
        List<Tweet> tweets = tweetService.findAll();
        model.addAttribute("tweetList", tweets);
        model.addAttribute("currUser", userService.getLoggedInUser());
        return "feed";
    }
    
    @GetMapping(value= {"/feed"})
    public String getFeed(Model model){
    	User user = userService.getLoggedInUser();
        List<Tweet> tweets = tweetService.findAllByUsers(user.getFollowing());
        model.addAttribute("currUser", userService.getLoggedInUser());
        model.addAttribute("tweetList", tweets);
        return "feed";
    }
    
    @GetMapping(value = "/tweets/new")
    public String getTweetForm(Model model) {
        model.addAttribute("tweet", new Tweet());
        model.addAttribute("currUser", userService.getLoggedInUser());
        return "newTweet";
    }
    
    @PostMapping(value = "/tweets")
    public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model) {
        User user = userService.getLoggedInUser();
        String returnString = "newTweet";
        if (!bindingResult.hasErrors()) {
            tweet.setUser(user);
            tweetService.save(tweet);
            model.addAttribute("successMessage", "Tweet successfully created!");
            model.addAttribute("tweet", new Tweet());
            returnString = "redirect:/";
        }
        return returnString;
    }
    
	public void SetTweetCounts(List<User> users, Model model) {
	    HashMap<String,Integer> tweetCounts = new HashMap<>();
	    for (User user : users) {
	        List<Tweet> tweets = tweetService.findAllByUser(user);
	        tweetCounts.put(user.getUsername(), tweets.size());
	    }
	    model.addAttribute("tweetCounts", tweetCounts);
	}
	
	@GetMapping(value = "/tweets/{tag}")
	public String getTweetsByTag(@PathVariable(value="tag") String tag, Model model) {
	    List<Tweet> tweets = tweetService.findAllWithTag(tag);
	    model.addAttribute("currUser", userService.getLoggedInUser());
	    model.addAttribute("tweetList", tweets);
	    model.addAttribute("tag", tag);
	    return "taggedTweets";
	}
}
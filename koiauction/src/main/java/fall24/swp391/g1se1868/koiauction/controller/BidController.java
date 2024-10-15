package fall24.swp391.g1se1868.koiauction.controller;


import fall24.swp391.g1se1868.koiauction.model.Bid;
import fall24.swp391.g1se1868.koiauction.model.BidRequest;
import fall24.swp391.g1se1868.koiauction.model.UserPrinciple;
import fall24.swp391.g1se1868.koiauction.service.AuctionService;
import fall24.swp391.g1se1868.koiauction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bid")
public class BidController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private BidService bidService;

    @Autowired
    private AuctionService auctionService;

    @PostMapping("/place")
    public ResponseEntity<?> placeBid(@RequestBody BidRequest bidRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        int userId = userPrinciple.getId();

        Bid bid = new Bid();
        bid.setAuctionID(auctionService.getAuctionById(bidRequest.getAuctionId()));
        bid.setAmount(bidRequest.getAmount());

        try {
            Bid newBid = bidService.placeBid(bid, userId);
            messagingTemplate.convertAndSend("/topic/auction/" + bid.getAuctionID().getId(), bidService.getAllBidsForAuction(bid.getAuctionID().getId()));
            return ResponseEntity.ok(newBid);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error.");
        }
    }

    @RequestMapping("/get-all")
    public List<Bid> getAllBid(@RequestParam int auctionId) {
        return bidService.getAllBidsForAuction(auctionId);
    }

}
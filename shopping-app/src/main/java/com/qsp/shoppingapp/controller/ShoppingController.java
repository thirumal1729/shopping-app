package com.qsp.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.shoppingapp.dto.ResponseStructure;
import com.qsp.shoppingapp.entity.Orders;
import com.qsp.shoppingapp.entity.Product;
import com.qsp.shoppingapp.entity.Review;
import com.qsp.shoppingapp.entity.User;
import com.qsp.shoppingapp.service.OrderService;
import com.qsp.shoppingapp.service.ProductService;
import com.qsp.shoppingapp.service.ReviewService;
import com.qsp.shoppingapp.service.UserService;
import com.qsp.shoppingapp.util.OrderHelper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
public class ShoppingController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Operation(description = "To create User", summary = "User will save to the data base")
	@ApiResponses(value = {@ApiResponse(description = "User Created",responseCode = "201"),@ApiResponse(description = "`Bad Request`", responseCode = "400", content = @Content)})
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@Operation(description = "To get user info", summary = "user details will show")
	@ApiResponses(value = {@ApiResponse(description = "OK",responseCode = "200"), @ApiResponse(description = "`Not Found`", responseCode = "404", content = @Content)})
	@GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<User>> getUser(@PathVariable int userId) {
		return userService.getUser(userId);
	}
	
	@Operation(description = "To create product", summary = "product will save")
	@ApiResponses(value = {@ApiResponse(description = "Product Created",responseCode = "201"), @ApiResponse(description = "`Bad Request`", responseCode = "400", content = @Content)})
	@PostMapping(value = "/merchants/{merchantId}/products",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Product>> saveProduct(@PathVariable int merchantId, @RequestBody Product product) {
		return productService.saveProduct(merchantId, product);
	}
	
	@Operation(description = "To get products info", summary = "products details will show")
	@ApiResponses(value = {@ApiResponse(description = "OK",responseCode = "200"), @ApiResponse(description = "`No Content`", responseCode = "204", content = @Content)})
	@GetMapping(value = "/merchants/{merchantId}/products", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts(@PathVariable int merchantId) {
		return productService.getAllProducts(merchantId);
	}
	
	
	@Operation(description = "To create order", summary = "order will save")
	@ApiResponses(value = {@ApiResponse(description = "Order Created",responseCode = "201"), @ApiResponse(description = "`Bad Request`", responseCode = "400", content = @Content)})
	@PostMapping(value = "/customers/{customerId}/orders",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Orders>> saveOrder(@PathVariable int customerId, @RequestBody OrderHelper orderHelper) {
		return orderService.saveOrder(customerId, orderHelper);
	}
	
	@Operation(description = "To get orders info", summary = "orders details will show")
	@ApiResponses(value = {@ApiResponse(description = "OK",responseCode = "200"), @ApiResponse(description = "`No Content`", responseCode = "204", content = @Content)})
	@GetMapping(value = "/customers/{customerId}/orders", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<List<Orders>>> getAllOrdersByCustomerId(@PathVariable int customerId) {
		return orderService.getAllOrdersByUserId(customerId);
	}
	
	@Operation(description = "To create review", summary = "review will save")
	@ApiResponses(value = {@ApiResponse(description = "Review Created",responseCode = "201"), @ApiResponse(description = "`Bad Request`", responseCode = "400", content = @Content), @ApiResponse(description = "`No Content`", responseCode = "204", content = @Content)})
	@PostMapping(value = "/customers/{customerId}/products/{productId}/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<Review>> saveReview(@PathVariable int customerId, @PathVariable int productId, @RequestBody Review review) {
		return reviewService.saveReview(customerId, productId, review);
	}
	
	@Operation(description = "To get reviews info", summary = "reviews details will show")
	@ApiResponses(value = {@ApiResponse(description = "OK",responseCode = "200"), @ApiResponse(description = "`No Content`", responseCode = "204", content = @Content)})
	@GetMapping(value = "/products/{productId}/reviews", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<List<Review>>> getAllReviewsByProductId(@PathVariable int productId) {
		return reviewService.getAllReviewsBYProductId(productId);
	}
	
	@Operation(description = "To delete review", summary = "products will be deleted")
	@ApiResponses(value = {@ApiResponse(description = "OK",responseCode = "200"), @ApiResponse(description = "`Not Found`", responseCode = "404", content = @Content), @ApiResponse(description = "`No Content`", responseCode = "204", content = @Content)})
	@DeleteMapping(value = "/{merchantId}/products/{productId}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<String>> deleteProductByProductId(@PathVariable int merchantId, @PathVariable int productId) {
		return reviewService.deleteReviewByReviewId(merchantId, productId);
	}
	
	@Operation(description = "To delete review", summary = "reviews will be deleted")
	@ApiResponses(value = {@ApiResponse(description = "OK",responseCode = "200"), @ApiResponse(description = "`Not Found`", responseCode = "404", content = @Content), @ApiResponse(description = "`No Content`", responseCode = "204", content = @Content)})
	@DeleteMapping(value = "/{merchantId}/reviews/{reviewId}", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseStructure<String>> deleteReviewByReviewId(@PathVariable int merchantId, @PathVariable int reviewId) {
		return reviewService.deleteReviewByReviewId(merchantId, reviewId);
	}
	
}

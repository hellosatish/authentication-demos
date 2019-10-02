package org.sk.security.demo.constants;

public class RoleConstants {

	/**
	 *  Can add new accounts and assign roles to them.
	 *	Can add new stores to available stores
	 */
	public static final String ROLE_ADMIN = "ADMIN";
	/*
	 * Can add new products to store 
	 * Can remove products from store
	 * 
	 */
	public static final String ROLE_STORE_MANAGER ="MANAGER";
	
	/* 
	 * Can view and buy products 
	 * 
	 */
	public static final String ROLE_STORE_USER ="USER";
	
}

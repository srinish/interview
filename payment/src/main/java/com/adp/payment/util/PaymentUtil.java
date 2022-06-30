package com.adp.payment.util;

import java.util.LinkedList;
import java.util.List;

/*
 * Utility class to get the minimum number of coins required
 */
public class PaymentUtil {
	
	static final int MAX = 100000;
	

	
	//Function to find the minimum
	//number of coins to make the
	//sum equals to X
	static int countMinCoins(Integer target,
							List<Integer> coins, Integer numberOfCoins, int[] dp)
	{
		//Base case
		if (target == 0)
		{
			dp[0] = 0;
			return 0;
		}
	
		//If previously computed
		//subproblem occurred
		if (dp[target] != -1)
			return dp[target];
	
		//Initialize result
		int ret = Integer.MAX_VALUE;
	
		//Try every coin that has smaller
		//value than n
		for (int i = 0; i < numberOfCoins; i++)
		{
			if (coins.get(i) <= target)
			{
			int minCoins = countMinCoins(target - coins.get(i),
									coins, numberOfCoins, dp);
		
			// Check for Integer.MAX_VALUE to avoid
			// overflow and see if result
			// can be minimized
			if (minCoins != Integer.MAX_VALUE)
				ret = Math.min(ret, 1 + minCoins);
			}
		}
	
		//Memoizing value of current state
		dp[target] = ret;
		return ret;
	}

	//Function to find the possible
	//combination of coins to make
	//the sum equal to X
	static void findSolution(Integer target,
							List<Integer> coins, Integer numberOfCoins, 		//List to store the result
							 List<Integer> denomination,
							 int[] dp)
	{
		//Base Case
		if (target == 0)
		{
			// Print Solutions
//			for (int it : denomination)
//			{
//			System.out.print(it + " ");
//			}
			return;
		}
	
		for (int i = 0; i < numberOfCoins; i++)
		{
			// Try every coin that has
			// value smaller than n
			if (target - coins.get(i) >= 0 &&
				dp[target - coins.get(i)] + 1 ==
				dp[target])
			{
			// Add current denominations
			denomination.add(coins.get(i));
		
			// Backtrack
			findSolution(target - coins.get(i), coins, numberOfCoins, denomination, dp);
			break;
			}
		}
	}

	//Function to find the minimum
	//combinations of coins for value X
	public static List<Integer> countMinCoinsUtil(Integer target,
								List<Integer> coins, Integer numberOfCoins)
	{
		//dp array to memoize the results
		 int []dp = new int[MAX + 1];
		
		//List to store the result
		 List<Integer> denomination =
					new LinkedList<Integer>();
		
		//Initialize dp with -1
		for (int i = 0; i < dp.length; i++)
			dp[i] = -1;
	
		//Min coins
		int isPossible = countMinCoins(target, coins, numberOfCoins, dp);
	
		//If no solution exists
		if (isPossible == Integer.MAX_VALUE)
		{
			return null;
		}
	
		//Backtrack to find the solution
		else
		{
			findSolution(target, coins, numberOfCoins, denomination, dp);
		}
		return denomination;
	}

}

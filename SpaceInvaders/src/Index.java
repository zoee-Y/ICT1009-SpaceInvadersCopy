/**
 * This Class manages all the unique identifiers of my objects, 
 * it makes sure they are all unique and in order
 * @author elan
 *
 */
public class Index 
{
	/**
	 * Updates the Bullets identifiers
	 */
	public static void updateBulletIndex()
	{
		for(int i = GameObjectManager.bullets.size() - 1; i >= 0; i--)
			if(GameObjectManager.bullets.get(i).getIndex() != i)
				GameObjectManager.bullets.get(i).updateIndex(i);
			else
				break;
	}

	/**
	 * Updates the Enemies identifiers
	 */
	public static void updateEnemyIndex()
	{
		for(int i = GameObjectManager.enemies.size() - 1; i >= 0; i--)
			if(GameObjectManager.enemies.get(i).getIndex() != i)
				GameObjectManager.enemies.get(i).updateIndex(i);
			else
				break;
	}

	/**
	 * Updates the EnemyBullets identifiers
	 */
	public static void updateEnemyBulletIndex()
	{
		for(int i = GameObjectManager.enemyBullets.size() - 1; i >= 0; i--)
			if(GameObjectManager.enemyBullets.get(i).getIndex() != i)
				GameObjectManager.enemyBullets.get(i).updateIndex(i);
			else
				break;
	}
	
	/**
	 * Updates the PowerUpBoxes identifiers
	 */
	public static void updatePowerUpBoxesIndex()
	{
		for(int i = GameObjectManager.powerUpBoxes.size() - 1; i >= 0; i--)
			if(GameObjectManager.powerUpBoxes.get(i).getIndex() != i)
				GameObjectManager.powerUpBoxes.get(i).updateIndex(i);
			else
				break;
	}
	
	/**
	 * Updates the Bunkers identifiers
	 */
	public static void updateBunkerIndex()
	{
		for(int i = GameObjectManager.bunkers.size() - 1; i >= 0; i--)
			if(GameObjectManager.bunkers.get(i).getIndex() != i)
				GameObjectManager.bunkers.get(i).updateIndex(i);
			else
				break;
	}
}

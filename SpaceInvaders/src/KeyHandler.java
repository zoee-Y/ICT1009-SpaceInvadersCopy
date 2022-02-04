import java.awt.event.KeyEvent;
/**
 * This Class handles all the key presses in my game
 * @author elan
 *
 */
public class KeyHandler
{
	/**
	 * checks the keyboard for input and handles it
	 */
	public static void processKey()
	{
		//movement
		if(StdDraw.isKeyPressed(KeyEvent.VK_A))//a
		{
			GameObjectManager.player.moveLeft(States.SHOOTER_MOVE_SPEED);
			
			if(!States.controlsKeyboard)
				GameObjectManager.player.setAngle();
			
			if(States.debug)
				System.out.println("KeyHandle: a");
		}

		if(StdDraw.isKeyPressed(KeyEvent.VK_D))//d
		{
			GameObjectManager.player.moveRight(States.SHOOTER_MOVE_SPEED);

			if(!States.controlsKeyboard)
				GameObjectManager.player.setAngle();
			
			if(States.debug)
				System.out.println("KeyHandle: d");
		}

		//rotation
		if(States.controlsKeyboard)
		{
			if(StdDraw.isKeyPressed(KeyEvent.VK_Q))//q
			{
				GameObjectManager.player.rotateAntiClockWise(States.SHOOTER_ROTATE_SPEED);

				if(States.debug)
					System.out.println("KeyHandle: q");
			}

			if(StdDraw.isKeyPressed(KeyEvent.VK_E))//e
			{
				GameObjectManager.player.rotateClockWise(States.SHOOTER_ROTATE_SPEED);

				if(States.debug)
					System.out.println("KeyHandle: e");
			}

			if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE))//space
			{
				GameObjectManager.player.shoot(GameObjectManager.player);

				if(States.debug)
					System.out.println("KeyHandle: space");
			}
		}
		
		//PLAYER 2
		//movement
		if(StdDraw.isKeyPressed(KeyEvent.VK_J))//j
		{
			GameObjectManager.player2.moveLeft(States.SHOOTER_MOVE_SPEED);
			
			if(!States.controlsKeyboard)
				GameObjectManager.player2.setAngle();
			
			if(States.debug)
				System.out.println("KeyHandle: j");
		}

		if(StdDraw.isKeyPressed(KeyEvent.VK_L))//l
		{
			GameObjectManager.player2.moveRight(States.SHOOTER_MOVE_SPEED);

			if(!States.controlsKeyboard)
				GameObjectManager.player2.setAngle();
			
			if(States.debug)
				System.out.println("KeyHandle: l");
		}

		//rotation
		if(States.controlsKeyboard)
		{
			if(StdDraw.isKeyPressed(KeyEvent.VK_U))//u
			{
				GameObjectManager.player2.rotateAntiClockWise(States.SHOOTER_ROTATE_SPEED);

				if(States.debug)
					System.out.println("KeyHandle: u");
			}

			if(StdDraw.isKeyPressed(KeyEvent.VK_O))//o
			{
				GameObjectManager.player2.rotateClockWise(States.SHOOTER_ROTATE_SPEED);

				if(States.debug)
					System.out.println("KeyHandle: o");
			}

			if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))//enter
			{
				GameObjectManager.player2.shoot(GameObjectManager.player2);

				if(States.debug)
					System.out.println("KeyHandle: enter");
			}
		}
		

		if(StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
		{
			GameObjectManager.exitConfirmation();
		}
	}
}
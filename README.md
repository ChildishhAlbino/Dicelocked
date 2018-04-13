# Stacked-Grid-client-
The client for a game I am developing for a TAFE assessment.

The game contains two 3x3 grids where each player can places pieces that fall into one of three classes; Rogues, Warriors, and Mages.

The game uses RPS mechanics where Rogues beat Mages who beat Warriors who beat Rogues.

Each classes contains 3 types which change the playstyle:

Rogues:
  Assassins - standard rogue class, can attack from range but only in a straight line and only in one plane. They can only attack the
  first person in the plane they strike in.
  
  Archers - less defensive than assassins or ninjas, they can attack with piercing arrows and hit all enemies in a line (damage reduces
  after each hit)
  
  Ninjas - high risk, high reward. Can attack and move in the same turn - each succesful kill also refunds the move. If they fail to kill
  a target, your turn is over.
  
Warriors:
  Swordsmen - generic "knight" character, can attack any space on the opposing field but they must be in the front row of the field
  they are on (and they cannot attack backwards) to compensate, they can defend any piece that is standing behind them.
  
  Shieldbearers - almost the opposite of the knight, they can only attack units in the opposing field's front row but can defend any piece
  within a 2x2 area of them
  
  Brutes - Balls-to-the-walls class; the brute can charge in a direction until it hits something - dealing supereffective damage to the first
  unit hit. However, that also causes it to take massive damage in return.
  
Mages:
  Sorcerors - typical mage class; can use spells to deal A.O.E damage but can only attack once per turn.
  
  Alchemists - can convert other units into different classes or buff them with bonuses, but cannot deal super effective damage 
  to other classes.
  
  Battle Mages - use magic to amplify their physical stats - they always deal super effective damage and take ineffectivce damage from all
  units except other battle mages.
  
  

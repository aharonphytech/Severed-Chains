package legend.game.combat.bent;

import legend.core.memory.Method;
import legend.game.characters.Element;
import legend.game.characters.ElementSet;
import legend.game.characters.StatCollection;
import legend.game.characters.StatType;
import legend.game.combat.types.AttackType;
import legend.game.combat.types.BattleObject;
import legend.game.combat.types.CombatantStruct1a8;
import legend.game.modding.coremod.CoreMod;
import legend.game.modding.events.battle.RegisterBattleEntityStatsEvent;
import legend.game.types.ItemStats0c;
import legend.game.types.Model124;
import legend.game.types.SpellStats0c;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.HashSet;
import java.util.Set;

import static legend.core.GameEngine.EVENTS;

public abstract class BattleEntity27c extends BattleObject {
  public final BattleEntityType type;

  public final StatCollection stats;

  /**
   * <ul>
   *   <li>0x1 - Petrified</li>
   *   <li>0x2 - Bewitched</li>
   *   <li>0x4 - Confused</li>
   *   <li>0x8 - Fearful</li>
   *   <li>0x10 - Stunned</li>
   *   <li>0x20 - Weapon blocked</li>
   *   <li>0x40 - Dispirited</li>
   *   <li>0x80 - Poison</li>
   *   <li>0x800 - Don't apply elemental effects for this attack (cleared after damage is done)</li>
   *   <li>0x2000 - Can become dragoon</li>
   *   <li>0x4000 - Divine Dragoon</li>
   * </ul>
   */
  public int status_0e;

  /**
   * <ul>
   *   <li>0x8 - Display "Attack All"</li>
   *   <li>0x40 - Chance to kill</li>
   *   <li>0x80 - Resistance</li>
   * </ul>
   */
  public int specialEffectFlag_14;
//  public int equipmentType_16;
  public int equipment_02_18;
  public int equipmentEquipableFlags_1a;

  public int equipment_05_1e;
  public final ElementSet equipmentElementalResistance_20 = new ElementSet();
  public final ElementSet equipmentElementalImmunity_22 = new ElementSet();
  /**
   * <ul>
   *   <li>0x1 - Petrify</li>
   *   <li>0x2 - Bewitchment</li>
   *   <li>0x4 - Confusion</li>
   *   <li>0x8 - Fear</li>
   *   <li>0x10 - Stun</li>
   *   <li>0x20 - Arm Block</li>
   *   <li>0x40 - Dispirit</li>
   *   <li>0x80 - Poison</li>
   * </ul>
   */
  public int equipmentStatusResist_24;
  public int equipment_09_26;
  public int equipmentAttack1_28;

  public int _2e;
  public int equipmentIcon_30;
  public int attack_34;
  public int magicAttack_36;
  public int defence_38;
  public int magicDefence_3a;
  public int attackHit_3c;
  public int magicHit_3e;
  public int attackAvoid_40;
  public int magicAvoid_42;
  /**
   * Player only - if you have a weapon that inflicts a status, this will be a %
   * <p>
   * Also used if specialEffect has one-hit-KO enabled
   */
  public int onHitStatusChance_44;
  public int equipment_19_46;
  public int equipment_1a_48;
  /**
   * Player only - if you have a weapon that inflicts a status, this will be set of statuses
   *
   * <ul>
   *   <li>0x1 - Petrify</li>
   *   <li>0x2 - Bewitchment</li>
   *   <li>0x4 - Confusion</li>
   *   <li>0x8 - Fear</li>
   *   <li>0x10 - Stun</li>
   *   <li>0x20 - Arm Block</li>
   *   <li>0x40 - Dispirit</li>
   *   <li>0x80 - Poison</li>
   * </ul>
   */
  public int equipmentOnHitStatus_4a;
  /** Determines turn order */
  public int turnValue_4c;
  public int spellId_4e;

  public int itemId_52;
  public int guard_54;

  /** Enemy aggressiveness with counters; lower means it can counter tighter timings; 0 = cannot counter */
  public int hitCounterFrameThreshold_7e;
  public int _80;
  public int _82;
  public int _84;
  public int _86;
  public int _88;
  public int _8a;

  public SpellStats0c spell_94;

  public int powerAttack_b4;
  public int powerAttackTurns_b5;
  public int powerMagicAttack_b6;
  public int powerMagicAttackTurns_b7;
  public int powerDefence_b8;
  public int powerDefenceTurns_b9;
  public int powerMagicDefence_ba;
  public int powerMagicDefenceTurns_bb;
  public int tempAttackHit_bc;
  public int tempAttackHitTurns_bd;
  public int tempMagicHit_be;
  public int tempMagicHitTurns_bf;
  public int tempAttackAvoid_c0;
  public int tempAttackAvoidTurns_c1;
  public int tempMagicAvoid_c2;
  public int tempMagicAvoidTurns_c3;
  public int tempPhysicalImmunity_c4;
  public int tempPhysicalImmunityTurns_c5;
  public int tempMagicalImmunity_c6;
  public int tempMagicalImmunityTurns_c7;

  public ItemStats0c item_d4;
  public int _ec;
  public int _ee;
  public int _f0;
  public int _f2;

  public boolean physicalImmunity_110;
  public boolean magicalImmunity_112;
  public boolean physicalResistance_114;
  public boolean magicalResistance_116;

  public int _142;
  public CombatantStruct1a8 combatant_144;
  public final Model124 model_148;
  public int combatantIndex_26c;
  /** Not 100% sure on this name */
  public int loadingAnimIndex_26e;
  /** Not 100% sure on this name */
  public int currentAnimIndex_270;
  /** Also monster ID */
  public int charId_272;
  public int bentSlot_274;
  public int charSlot_276;
  /** Has model? Used to be used to free model, no longer used since it's managed by java */
  public int _278;

  private final Vector3i colour = new Vector3i(0x80, 0x80, 0x80);

  public BattleEntity27c(final BattleEntityType type, final String name) {
    this.type = type;
    this.model_148 = new Model124(name);

    final Set<StatType> stats = new HashSet<>();
    EVENTS.postEvent(new RegisterBattleEntityStatsEvent(type, stats));
    this.stats = new StatCollection(stats.toArray(StatType[]::new));
  }

  public int getEffectiveDefence() {
    return this.defence_38;
  }

  public int getEffectiveMagicDefence() {
    return this.magicDefence_3a;
  }

  public abstract ElementSet getAttackElements();
  public abstract Element getElement();

  public abstract int calculatePhysicalDamage(final BattleEntity27c target);
  /**
   * @param magicType item (0), spell (1)
   */
  public abstract int calculateMagicDamage(final BattleEntity27c target, final int magicType);

  public int applyPhysicalDamageMultipliers(final int damage) {
    return damage;
  }

  public void applyAttackEffects() {

  }

  @Method(0x800f29d4L)
  public int applyDamageResistanceAndImmunity(final int damage, final AttackType attackType) {
    if(attackType.isPhysical()) {
      if(this.physicalImmunity_110) {
        return 0;
      }

      if(this.physicalResistance_114) {
        return damage / 2;
      }
    }

    if(attackType.isMagical()) {
      if(this.magicalImmunity_112) {
        return 0;
      }

      if(this.magicalResistance_116) {
        return damage / 2;
      }
    }

    return damage;
  }

  public int applyElementalResistanceAndImmunity(final int damage, final Element element) {
    if(this.equipmentElementalImmunity_22.contains(element)) {
      return 0;
    }

    return damage;
  }

  public void turnFinished() {
    this.tickTemporaryStatMod(this, BattleEntityStat.POWER_ATTACK);
    this.tickTemporaryStatMod(this, BattleEntityStat.POWER_MAGIC_ATTACK);
    this.tickTemporaryStatMod(this, BattleEntityStat.POWER_DEFENCE);
    this.tickTemporaryStatMod(this, BattleEntityStat.POWER_MAGIC_DEFENCE);
    this.tickTemporaryStatMod(this, BattleEntityStat.TEMP_ATTACK_HIT);
    this.tickTemporaryStatMod(this, BattleEntityStat.TEMP_MAGIC_HIT);
    this.tickTemporaryStatMod(this, BattleEntityStat.TEMP_ATTACK_AVOID);
    this.tickTemporaryStatMod(this, BattleEntityStat.TEMP_MAGIC_AVOID);
    this.tickTemporaryStatMod(this, BattleEntityStat.TEMP_PHYSICAL_IMMUNITY);
    this.tickTemporaryStatMod(this, BattleEntityStat.TEMP_MAGICAL_IMMUNITY);

    this.stats.turnFinished(this);
  }

  @Method(0x800f3204L)
  public void recalculateSpeedAndPerHitStats() {

  }

  protected void tickTemporaryStatMod(final BattleEntity27c bent, final BattleEntityStat stat) {
    if(bent.getStat(stat) != 0) {
      if((bent.getStat(stat) & 0xff00) < 0x200) { // Turns is stored in upper byte
        bent.setStat(stat, 0);
      } else {
        bent.setStat(stat, bent.getStat(stat) - 0x100); // Subtract one turn
      }
    }
  }

  @Deprecated
  public int getStat(final BattleEntityStat statIndex) {
    return switch(statIndex) {
      case CURRENT_HP -> this.stats.getStat(CoreMod.HP_STAT.get()).getCurrent();

      case STATUS -> this.status_0e;
      case MAX_HP -> this.stats.getStat(CoreMod.HP_STAT.get()).getMax();

      case SPECIAL_EFFECT_FLAGS -> this.specialEffectFlag_14;
//      case EQUIPMENT_TYPE -> this.equipmentType_16;
      case EQUIPMENT_02 -> this.equipment_02_18;
      case EQUIPMENT_EQUIPABLE_FLAGS -> this.equipmentEquipableFlags_1a;

      case EQUIPMENT_05 -> this.equipment_05_1e;
      case EQUIPMENT_ELEMENTAL_RESISTANCE -> this.equipmentElementalResistance_20.pack();
      case EQUIPMENT_ELEMENTAL_IMMUNITY -> this.equipmentElementalImmunity_22.pack();
      case EQUIPMENT_STATUS_RESIST -> this.equipmentStatusResist_24;
      case EQUIPMENT_09 -> this.equipment_09_26;
      case EQUIPMENT_ATTACK -> this.equipmentAttack1_28;

      case _21 -> this._2e;
      case EQUIPMENT_ICON -> this.equipmentIcon_30;
      case SPEED -> this.stats.getStat(CoreMod.SPEED_STAT.get()).get();
      case ATTACK -> this.attack_34;
      case MAGIC_ATTACK -> this.magicAttack_36;
      case DEFENCE -> this.defence_38;
      case MAGIC_DEFENCE -> this.magicDefence_3a;
      case ATTACK_HIT -> this.attackHit_3c;
      case MAGIC_HIT -> this.magicHit_3e;
      case ATTACK_AVOID -> this.attackAvoid_40;
      case MAGIC_AVOID -> this.magicAvoid_42;
      case ON_HIT_STATUS_CHANCE -> this.onHitStatusChance_44;
      case EQUIPMENT_19 -> this.equipment_19_46;
      case EQUIPMENT_1a -> this.equipment_1a_48;
      case EQUIPMENT_ON_HIT_STATUS -> this.equipmentOnHitStatus_4a;
      case TURN_VALUE -> this.turnValue_4c;
      case SPELL_ID -> this.spellId_4e;

      case ITEM_ID -> this.itemId_52;
      case GUARD -> this.guard_54;

      case HIT_COUNTER_FRAME_THRESHOLD -> this.hitCounterFrameThreshold_7e;
      case _62 -> this._80;
      case _63 -> this._82;
      case _64 -> this._84;
      case _65 -> this._86;
      case _66 -> this._88;
      case _67 -> this._8a;

      case SPELL_TARGET_TYPE -> this.spell_94.targetType_00;
      case SPELL_FLAGS -> this.spell_94.flags_01;
      case SPELL_SPECIAL_EFFECT -> this.spell_94.specialEffect_02;
      case SPELL_DAMAGE_MULTIPLIER -> this.spell_94.damageMultiplier_03;
      case SPELL_MULTI -> this.spell_94.multi_04;
      case SPELL_ACCURACY -> this.spell_94.accuracy_05;
      case SPELL_MP -> this.spell_94.mp_06;
      case SPELL_STATUS_CHANCE -> this.spell_94.statusChance_07;
      case SPELL_ELEMENT -> this.spell_94.element_08.flag;
      case SPELL_STATUS_TYPE -> this.spell_94.statusType_09;
      case SPELL_BUFF_TYPE -> this.spell_94.buffType_0a;
      case SPELL_0b -> this.spell_94._0b;

      case POWER_ATTACK -> (this.powerAttackTurns_b5 & 0xff) << 8 | this.powerAttack_b4 & 0xff;
      case POWER_MAGIC_ATTACK -> (this.powerMagicAttackTurns_b7 & 0xff) << 8 | this.powerMagicAttack_b6 & 0xff;
      case POWER_DEFENCE -> (this.powerDefenceTurns_b9 & 0xff) << 8 | this.powerDefence_b8 & 0xff;
      case POWER_MAGIC_DEFENCE -> (this.powerMagicDefenceTurns_bb & 0xff) << 8 | this.powerMagicDefence_ba & 0xff;
      case TEMP_ATTACK_HIT -> (this.tempAttackHitTurns_bd & 0xff) << 8 | this.tempAttackHit_bc & 0xff;
      case TEMP_MAGIC_HIT -> (this.tempMagicHitTurns_bf & 0xff) << 8 | this.tempMagicHit_be & 0xff;
      case TEMP_ATTACK_AVOID -> (this.tempAttackAvoidTurns_c1 & 0xff) << 8 | this.tempAttackAvoid_c0 & 0xff;
      case TEMP_MAGIC_AVOID -> (this.tempMagicAvoidTurns_c3 & 0xff) << 8 | this.tempMagicAvoid_c2 & 0xff;
      case TEMP_PHYSICAL_IMMUNITY -> (this.tempPhysicalImmunityTurns_c5 & 0xff) << 8 | this.tempPhysicalImmunity_c4 & 0xff;
      case TEMP_MAGICAL_IMMUNITY -> (this.tempMagicalImmunityTurns_c7 & 0xff) << 8 | this.tempMagicalImmunity_c6 & 0xff;

      case ITEM_TARGET -> this.item_d4.target_00;
      case ITEM_ELEMENT -> this.item_d4.element_01.flag;
      case ITEM_DAMAGE_MULTIPLIER -> this.item_d4.damageMultiplier_02;

      case ITEM_DAMAGE -> this.item_d4.damage_05;

      case ITEM_ICON -> this.item_d4.icon_07;
      case ITEM_STATUS -> this.item_d4.status_08;
      case ITEM_PERCENTAGE -> this.item_d4.percentage_09;
      case ITEM_UU2 -> this.item_d4.uu2_0a;
      case ITEM_TYPE -> this.item_d4.type_0b;
      case _116 -> this._ec;
      case _117 -> this._ee;
      case _118 -> this._f0;
      case _119 -> this._f2;

      case PHYSICAL_IMMUNITY -> this.physicalImmunity_110 ? 1 : 0;
      case MAGICAL_IMMUNITY -> this.magicalImmunity_112 ? 1 : 0;
      case PHYSICAL_RESISTANCE -> this.physicalResistance_114 ? 1 : 0;
      case MAGICAL_RESISTANCE -> this.magicalResistance_116 ? 1 : 0;

      case _159 -> this._142;

      default -> throw new IllegalArgumentException("Some other stat that I haven't implemented " + statIndex);
    };
  }

  @Deprecated
  public void setStat(final BattleEntityStat statIndex, final int value) {
    switch(statIndex) {
      case CURRENT_HP -> this.stats.getStat(CoreMod.HP_STAT.get()).setCurrent(value);

      case STATUS -> this.status_0e = value;

      case SPECIAL_EFFECT_FLAGS -> this.specialEffectFlag_14 = value;
//      case EQUIPMENT_TYPE -> this.equipmentType_16 = value;
      case EQUIPMENT_02 -> this.equipment_02_18 = value;
      case EQUIPMENT_EQUIPABLE_FLAGS -> this.equipmentEquipableFlags_1a = value;

      case EQUIPMENT_05 -> this.equipment_05_1e = value;
      case EQUIPMENT_ELEMENTAL_RESISTANCE -> this.equipmentElementalResistance_20.unpack(value);
      case EQUIPMENT_ELEMENTAL_IMMUNITY -> this.equipmentElementalImmunity_22.unpack(value);
      case EQUIPMENT_STATUS_RESIST -> this.equipmentStatusResist_24 = value;
      case EQUIPMENT_09 -> this.equipment_09_26 = value;
      case EQUIPMENT_ATTACK -> this.equipmentAttack1_28 = value;

      case _21 -> this._2e = value;
      case EQUIPMENT_ICON -> this.equipmentIcon_30 = value;
      case ATTACK -> this.attack_34 = value;
      case MAGIC_ATTACK -> this.magicAttack_36 = value;
      case DEFENCE -> this.defence_38 = value;
      case MAGIC_DEFENCE -> this.magicDefence_3a = value;
      case ATTACK_HIT -> this.attackHit_3c = value;
      case MAGIC_HIT -> this.magicHit_3e = value;
      case ATTACK_AVOID -> this.attackAvoid_40 = value;
      case MAGIC_AVOID -> this.magicAvoid_42 = value;
      case ON_HIT_STATUS_CHANCE -> this.onHitStatusChance_44 = value;
      case EQUIPMENT_19 -> this.equipment_19_46 = value;
      case EQUIPMENT_1a -> this.equipment_1a_48 = value;
      case EQUIPMENT_ON_HIT_STATUS -> this.equipmentOnHitStatus_4a = value;
      case TURN_VALUE -> this.turnValue_4c = value;
      case SPELL_ID -> this.spellId_4e = value;

      case ITEM_ID -> this.itemId_52 = value;
      case GUARD -> this.guard_54 = value;

      case HIT_COUNTER_FRAME_THRESHOLD -> this.hitCounterFrameThreshold_7e = value;
      case _62 -> this._80 = value;
      case _63 -> this._82 = value;
      case _64 -> this._84 = value;
      case _65 -> this._86 = value;
      case _66 -> this._88 = value;
      case _67 -> this._8a = value;

      case POWER_ATTACK -> {
        this.powerAttack_b4 = (byte)value;
        this.powerAttackTurns_b5 = value >>> 8 & 0xff;
      }
      case POWER_MAGIC_ATTACK -> {
        this.powerMagicAttack_b6 = (byte)value;
        this.powerMagicAttackTurns_b7 = value >>> 8 & 0xff;
      }
      case POWER_DEFENCE -> {
        this.powerDefence_b8 = (byte)value;
        this.powerDefenceTurns_b9 = value >>> 8 & 0xff;
      }
      case POWER_MAGIC_DEFENCE -> {
        this.powerMagicDefence_ba = (byte)value;
        this.powerMagicDefenceTurns_bb = value >>> 8 & 0xff;
      }
      case TEMP_ATTACK_HIT -> {
        this.tempAttackHit_bc = value & 0xff;
        this.tempAttackHitTurns_bd = value >>> 8 & 0xff;
      }
      case TEMP_MAGIC_HIT -> {
        this.tempMagicHit_be = value & 0xff;
        this.tempMagicHitTurns_bf = value >>> 8 & 0xff;
      }
      case TEMP_ATTACK_AVOID -> {
        this.tempAttackAvoid_c0 = value & 0xff;
        this.tempAttackAvoidTurns_c1 = value >>> 8 & 0xff;
      }
      case TEMP_MAGIC_AVOID -> {
        this.tempMagicAvoid_c2 = value & 0xff;
        this.tempMagicAvoidTurns_c3 = value >>> 8 & 0xff;
      }
      case TEMP_PHYSICAL_IMMUNITY -> {
        this.tempPhysicalImmunity_c4 = value & 0xff;
        this.tempPhysicalImmunityTurns_c5 = value >>> 8 & 0xff;
      }
      case TEMP_MAGICAL_IMMUNITY -> {
        this.tempMagicalImmunity_c6 = value & 0xff;
        this.tempMagicalImmunityTurns_c7 = value >>> 8 & 0xff;
      }

      case _116 -> this._ec = value;
      case _117 -> this._ee = value;
      case _118 -> this._f0 = value;
      case _119 -> this._f2 = value;

      case PHYSICAL_IMMUNITY -> this.physicalImmunity_110 = value != 0;
      case MAGICAL_IMMUNITY -> this.magicalImmunity_112 = value != 0;
      case PHYSICAL_RESISTANCE -> this.physicalResistance_114 = value != 0;
      case MAGICAL_RESISTANCE -> this.magicalResistance_116 = value != 0;

      case _159 -> this._142 = value;

      default -> throw new IllegalArgumentException("Some other stat that I haven't implemented " + statIndex);
    }
  }

  @Override
  public Vector3f getPosition() {
    return this.model_148.coord2_14.coord.transfer;
  }

  @Override
  public Vector3f getRotation() {
    return this.model_148.coord2_14.transforms.rotate;
  }

  @Override
  public Vector3f getScale() {
    return this.model_148.coord2_14.transforms.scale;
  }

  @Override
  public Vector3i getColour() {
    return this.colour; // defaultEffectColour_800fb94c;
  }
}
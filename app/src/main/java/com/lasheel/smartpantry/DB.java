package com.lasheel.smartpantry;
import android.content.*;import android.database.*;import android.database.sqlite.*;import java.util.*;
public class DB extends SQLiteOpenHelper {
 public DB(Context c){super(c,"pantry.db",null,1);} public void onCreate(SQLiteDatabase db){db.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,qty REAL NOT NULL,unit TEXT NOT NULL,expiry TEXT)");db.execSQL("CREATE TABLE recipes(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,ingredients TEXT,steps TEXT)");
addRecipe(db,"Buttered Toast","bread:2:slice,butter:10:g","Toast the bread and spread butter.");
addRecipe(db,"Scrambled Eggs","egg:2:piece,butter:5:g","Beat eggs and cook in melted butter.");
addRecipe(db,"Boiled Eggs","egg:2:piece,water:300:ml","Boil eggs in water until cooked.");
addRecipe(db,"Tomato Salad","tomato:2:piece,salt:2:g","Slice tomatoes and season.");
addRecipe(db,"Cheese Toast","bread:2:slice,cheese:30:g","Place cheese on toast and warm.");
addRecipe(db,"Egg Sandwich","bread:2:slice,egg:1:piece","Cook egg and place between bread slices.");
addRecipe(db,"Tomato Sandwich","bread:2:slice,tomato:1:piece","Slice tomato and add to bread.");
addRecipe(db,"Cheese Sandwich","bread:2:slice,cheese:25:g","Add cheese between bread slices.");
addRecipe(db,"Banana Oats","banana:1:piece,oats:50:g,milk:150:ml","Mix oats and milk, top with banana.");
addRecipe(db,"Porridge","oats:50:g,water:200:ml","Simmer oats with water.");
addRecipe(db,"Rice Bowl","rice:100:g,water:200:ml","Cook rice in water.");
addRecipe(db,"Pasta with Butter","pasta:100:g,butter:15:g,water:500:ml","Boil pasta, drain and add butter.");
addRecipe(db,"Garlic Toast","bread:2:slice,garlic:5:g,butter:10:g","Mix garlic and butter and toast on bread.");
addRecipe(db,"Fruit Salad","apple:1:piece,banana:1:piece","Chop fruit and combine.");
addRecipe(db,"Milk Banana Shake","banana:1:piece,milk:200:ml","Blend banana and milk.");
addRecipe(db,"Tomato Omelette","egg:2:piece,tomato:1:piece,oil:10:ml","Beat eggs with chopped tomato and fry.");
addRecipe(db,"Fried Potato","potato:2:piece,oil:20:ml,salt:2:g","Slice potato and fry in oil.");
addRecipe(db,"Carrot Salad","carrot:2:piece,lemon:1:piece","Grate carrots and add lemon juice.");
addRecipe(db,"Peanut Butter Toast","bread:2:slice,peanut butter:20:g","Spread peanut butter on toast.");
addRecipe(db,"Cheesy Pasta","pasta:100:g,cheese:40:g,water:500:ml","Boil pasta, drain and stir in cheese.");
 } public void onUpgrade(SQLiteDatabase d,int oldV,int newV){} static void addRecipe(SQLiteDatabase d,String n,String ingredients,String steps){ContentValues v=new ContentValues();v.put("name",n);v.put("ingredients",ingredients);v.put("steps",steps);d.insert("recipes",null,v);} Cursor pantry(){return getReadableDatabase().rawQuery("SELECT * FROM pantry ORDER BY name",null);} Cursor recipes(){return getReadableDatabase().rawQuery("SELECT * FROM recipes ORDER BY name",null);} Cursor recipe(int id){return getReadableDatabase().rawQuery("SELECT * FROM recipes WHERE id=?",new String[]{""+id});} Cursor item(int id){return getReadableDatabase().rawQuery("SELECT * FROM pantry WHERE id=?",new String[]{""+id});}
 void save(int id,String name,double qty,String unit,String expiry){ContentValues v=new ContentValues();v.put("name",name.trim());v.put("qty",qty);v.put("unit",unit);v.put("expiry",expiry);if(id<0)getWritableDatabase().insert("pantry",null,v);else getWritableDatabase().update("pantry",v,"id=?",new String[]{""+id});} void delete(int id){getWritableDatabase().delete("pantry","id=?",new String[]{""+id});}
 static String norm(String s){s=s.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+"," ");if(s.equals("tomatoes"))return "tomato";if(s.equals("potatoes"))return "potato";if(s.equals("eggs"))return "egg";if(s.endsWith("s")&&!s.endsWith("ss")&&!s.equals("oats"))return s.substring(0,s.length()-1);return s;}
 static double amount(double q,String u){u=u.trim().toLowerCase(Locale.ROOT);if(u.equals("kg"))return q*1000;if(u.equals("l"))return q*1000;return q;} static String family(String u){u=u.trim().toLowerCase(Locale.ROOT);if(u.equals("kg")||u.equals("g"))return "g";if(u.equals("l")||u.equals("ml"))return "ml";if(u.equals("pieces")||u.equals("piece")||u.equals("pcs"))return "piece";if(u.equals("slices")||u.equals("slice"))return "slice";return u;}
 boolean matches(String requirements){for(String entry:requirements.split(",")){String[] parts=entry.split(":");String n=norm(parts[0]),u=parts[2];double needed=amount(Double.parseDouble(parts[1]),u),have=0;try(Cursor c=pantry()){while(c.moveToNext()){if(norm(c.getString(1)).equals(n)&&family(c.getString(3)).equals(family(u)))have+=amount(c.getDouble(2),c.getString(3));}}if(have+0.00001<needed)return false;}return true;}
}
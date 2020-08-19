# ojdbc6.jarをプロジェクトに追加する

## Eclipse作業  
プロジェクトのコンテキストメニュー > ビルド・パス > 外部アーカイブの追加 > ojdbc6.jarを選択  
> C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib\ojdbc6.jar

参照ライブラリーに追加され、Class.forNameの実行エラーが解消される。

# Eclipseによる実行可能jarの作成

## Eclipse作業
プロジェクトのコンテキストメニュー > エクスポート > 実行可能jarの作成  
起動構成を選択（1つだけ選択できるはず）  
エクスポート先を選択（どこでもよい）  

## コマンドプロンプト作業
実行確認  
> java -jar foo.jar

# コマンドによるコンパイルと実行

## コマンドプロンプト作業

適当なフォルダに移動して、クラスファイルを出力するフォルダ（例：workフォルダ）を作成する。
> cd tmp  
> mkdir work  

コンパイル
> javac -d work C:\pleiades-e4.5-java_20160312\pleiades\workspace\com.example.db.connect\src\com\example\db\connect\Main.java

実行確認（クラスパスにojdbc6.jarを追加する）
> java -classpath work;C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib\ojdbc6.jar com.example.db.connect.Main

# jarファイルの作成と実行

## エディタ作業
manifest.txtの作成  
> Main-Class: com.example.db.connect.Main  
> Class-Path: ojdbc6.jar  

## コマンドプロンプト作業
jarの作成
> jar -cfm foo.jar manifest.txt -C work .  

実行確認  
ojdbc6.jarをfoo.jarと同じフォルダにコピーしてから実行する。  
> java -jar foo.jar  

### 補足
Eclipseでjarを作成する時とは違い、foo.jarにojdbc6.jarを追加することはできない。  
foo.jarをfoo.jar.zipに名前変更すれば中身を確認できる。  
中身を見比べると内情を察することができる。

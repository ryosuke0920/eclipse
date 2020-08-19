# ojdbc6.jarをプロジェクトに追加する

* プロジェクトのコンテキストメニュー

* ビルド・パス

* 外部アーカイブの追加

* ojdbc6.jarを選択
C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib

* 参照ライブラリーに追加される。


# 実行可能jarの作成

* プロジェクトのコンテキストメニュー

* エクスポート

* 実行可能jarの作成
起動構成を選択
エクスポート先を選択

* 実行確認

> java -jar foo.jar


# コマンドによるコンパイルと実行

* コンパイル
srcディレクトリに移動する。
com\example\db\connect\Main.class を作成する。
> javac com\example\db\connect\Main.java

* 実行
> java -classpath C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib\ojdbc6.jar;./ com.example.db.connect.Main


# jarファイルの作成と実行

所定のフォルダ（.\work）にclassファイルを作成する。
> javac -d .\work C:\pleiades-e4.5-java_20160312\pleiades\workspace\com.example.db.connect\src\com\example\db\connect\Main.java


manifest.txtの作成
> Main-Class: com.example.db.connect.Main
> Class-Path: ojdbc6.jar


* jarの作成

> jar -cfm abc.jar manifest.txt -C work .

* jarの実行
ojdbc6.jarをabc.jarと同じフォルダに置いてから実行する
> java -jar abc.jar


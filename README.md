# students-api

## 実行方法

### Docker

1. https://github.com/tkfmst/students-api/releases/tag/v0.2.0 からapi-0.2.0.jarをダウンロードしカレントディレクトリに設置
    - 場所を変える場合はDockerfileを修正してください
2. dockerイメージを作成
    - ```
      $ docker build -t students-api
      ```
3. dockerを起動すると自動でサーバーを起動する
    - ```
      $ docker run -it -p8080:8008 students-api
      ```

### Jarから起動

1. https://github.com/tkfmst/students-api/releases/tag/v0.2.0 からapi-0.2.0.jarをダウンロード
2. 以下で起動
    - ```
      $ java -jar api-0.2.0.jar   
      ```

### sbt

1. https://github.com/tkfmst/students-api/releases/tag/v0.2.0 のソースをcloneしてくる
2. 以下のコマンドで実行
    - ```
      api/runMain com.example.api.Server
      ```

## 動作確認手順

サーバーはport8080で起動しているので以下のようなリクエストで確認できます
```
 curl -s -XGET 'http://127.0.0.1:8080/students?facilitator_id=3&page=1&limit=3&sort=id&order=desc&classroom.name_like=%E7%89%B9%E9%80%B2'| jq .
```

正常に動いていれば、以下のリクエストを返します。
```
{
  "students": [
    {
      "id": 3,
      "name": "生徒 その3",
      "classroom": {
        "id": 2,
        "name": "特進クラス"
      }
    },
    {
      "id": 2,
      "name": "Student2",
      "classroom": {
        "id": 2,
        "name": "特進クラス"
      }
    }
  ],
  "totalCount": 2
}
```

## API仕様

簡易的には以下を参照ください
https://github.com/tkfmst/students-api/blob/main/doc/openapi.md

詳細には以下のyamlをswagger-UIで確認下さい
https://github.com/tkfmst/students-api/blob/main/doc/openapi.yaml

## 説明

クリーンアーキテクチャとRDBを組み合わせたシンプルな組み合わせ。
管理画面等で教師(or システム管理者)が自分の受け持ちクラスの生徒の一覧を表示する画面、のイメージでAPIを実装している。

レイヤー的には以下で、基本上から下への依存方向になる。
　
| レイヤー  | 役割                       | 備考                                                                             |
|-----------|----------------------------|----------------------------------------------------------------------------------|
| api       | http関連の処理を担当       |                                                                                  |
| infra     | Framework & Driver         | 今回は主にRDB。上のapiも本来このレイヤーだが、使いまわししない箇所なので分離した |
| interface | Interface Adapter          |                                                                                  |
| app       | Application Business Rules |                                                                                  |
| entity    | Enterprise Business Rules  |                                                                                  |
| types     | 独自型の管理               | このレイヤーは全てのレイヤーから利用可能                                         |


DBは特に珍しい構造はない。
一般的な正規化のみ。

- [ER図](https://github.com/tkfmst/students-api/blob/main/doc/ER-Diagram.drawio.png)
- [初期データ](https://github.com/tkfmst/students-api/blob/main/modules/infra/src/main/scala/com/example/infra/h2/util/CreateInitialData.scala)




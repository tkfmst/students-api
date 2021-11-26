# students
## Version: 0.0.1

### /students

#### GET
##### Summary

Find students by facilitator_id

##### Description

Returns students

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| facilitator_id | query | 先生のID | Yes | integer |
| page | query | 表示するページ番号 | No | integer |
| limit | query | 1ページに表示する生徒数 | No | integer |
| sort | query | ソートキー<br>Studentのキーを指定 | No | string |
| order | query | ソートの昇順(desc)・降順(asc)の選択 | No | string |
| {key}_like | query | 指定したStudentのキーによる部分一致検索<br>利用可能なキーはexamplesを参照 | No | object |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |

### Models

#### StudentList

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| students | [ object ] |  | No |
| total_count | integer | _Example:_ `1` | No |

#### Student

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | integer | _Example:_ `1` | No |
| name | string | _Example:_ `"生徒1"` | No |
| classroom | object |  | No |

#### Classroom

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | integer | _Example:_ `1` | No |
| name | string | _Example:_ `"特進クラス"` | No |

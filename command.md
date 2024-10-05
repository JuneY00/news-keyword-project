curl -X POST -H "Content-Type: application/json" http://localhost:8080/api/crawler/save
curl -X GET "localhost:9200/"


Spring Boot 애플리케이션을 실행한 후, POST /api/news/save API를 사용하여 데이터를 Elasticsearch에 저장하고, GET /api/news/author/{author} API로 데이터를 검색할 수 있어.


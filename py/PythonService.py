from flask import Flask, jsonify, request

app = Flask(__name__)

# 路由設置
@app.route("/api/data", methods=["GET"])
def get_data():
    # 從查詢參數中獲取 'message' 值
    message = request.args.get("message", "No message provided")
    return jsonify({"message": message})

if __name__ == "__main__":
    app.run(host="127.0.0.1", port=5000)

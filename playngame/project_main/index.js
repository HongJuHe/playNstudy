var display = function () {
          let e = document.createElement("script");
          (e.src =
            "https://cdn.jsdelivr.net/npm/rasa-webchat/lib/index.js"),
            (e.async = !0),
            (e.onload = () => {
                window.WebChat.default(
                {
                    initPayload: '/greet',
                    customData: { language: "en" },
                    socketUrl: "http://localhost:5005"
                },
                null
                );
            })
        };

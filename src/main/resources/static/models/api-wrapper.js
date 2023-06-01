const apiWrapper = {
    ajaxOption: {
        "Content-type": "application/json",
    },

    async get(endpoint, params) {
        try {
            const resp = await webix.ajax().headers(this.ajaxOption).get(endpoint, params);
//            console.log(resp);
//            console.log(resp.json());
            if (resp.json() == null)
                window.location.href = "/page/login";
            //success
            return resp.json();
        } catch (err) {
            return apiWrapper.errConverter(err);
        }
    },

    async post(endpoint, params) {
        try {
            const resp = await webix.ajax().headers(this.ajaxOption).post(endpoint, params);
            if (resp.json() == null)
                window.location.href = "/page/login";
            //success
            return resp.json();
        } catch (err) {
            return apiWrapper.errConverter(err);
        }
    },

    async put(endpoint, params) {
        try {
            const resp = await webix.ajax().headers(this.ajaxOption).put(endpoint, params);
            if (resp.json() == null) window.location.href = "/page/login";
            //success
            return resp.json();
        } catch (err) {
            return apiWrapper.errConverter(err);
        }
    },

    async del(endpoint, params) {
        try {
            const resp = await webix.ajax().headers(this.ajaxOption).del(endpoint, params);
            if (resp.json() == null) window.location.href = "/page/login";
            //success
            return resp.json();
        } catch (err) {
            return apiWrapper.errConverter(err);
        }
    },

    errConverter(err) {
        console.log(err);
        try{
            const res  = JSON.parse(err.responseText)
            return {status: "90", info: res.info};
        }catch (e){
            return {status: "90", info: err.info};
        }
    }
  };

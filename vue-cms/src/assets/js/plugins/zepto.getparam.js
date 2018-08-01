/**
 * Created by shirley on 06/04/2017.
 */
const utils = {
    getParam(key) {
        let search = window.location.href;
        let params = {};
        try {
            search = search.substring(search.indexOf('?') + 1)
            search = search.split("&");
            search.forEach((s, i) => {
                let _s = s.split("=");
                if (_s.length == 2) {
                    params[_s[0]] = _s[1];
                }
            });
        } catch (e) {}

        return params[key];
    }
};

export default utils;

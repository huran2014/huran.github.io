import api from './api'; // 倒入 api

/* 将所有接口统一起来便于维护
 * 如果项目很大可以将 url 独立成文件，接口分成不同的模块
 */

// 单独倒出
export const userinfo = params => {
    return api({
        url: '/family/user/info',
        method: 'get',
        params
    });
};

export default {
    userinfo
};

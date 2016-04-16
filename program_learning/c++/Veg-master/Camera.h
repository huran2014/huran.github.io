/***********************************************************************
 * Module:  Camera.h
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:01:38
 * Purpose: Declaration of the class Camera
 * Comment: ????ͷ??????
 ***********************************************************************/

#include <stdio.h>
#include <string>
#include <iostream>
#include <cstring>
#include <errno.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <assert.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <linux/videodev2.h>
#define V4L2_PIX_FMT_MJPEG 0
#define V4L2_PIX_FMT_YUV 1         

#if !defined (__Veg_Camera_h)
#define __Veg_Camera_h

class Camera
{
  public:
        //开启摄像头
        int OpenCamera(std::string errStr);
        //设置摄像头
        int SetCamera(int format,int width,int height,std::string errStr);
        //进行抓取照片
        int GetFrame(int buf,int weight,int lenght,std::string errStr);
        //关闭摄像头
        int CloseCamera(std::string errStr);
  private:
         int fd;
};

typedef struct
{
        void *start;
        int length;
}BUFTYPE;

BUFTYPE *user_buf;
int n_buffer = 0;
char *_pictureName;


#endif

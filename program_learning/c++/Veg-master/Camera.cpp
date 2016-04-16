/***********************************************************************
 * Module:  Camera.cpp
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:01:38
 * Purpose: Implementation of the class Camera
 * Comment: ????ͷ??????
 ***********************************************************************/
#include <iostream>
#include <string>
#include <cstring>
#include "Camera.h"

////////////////////////////////////////////////////////////////////////
// Name:       Camera::OpenCamera(std::string errStr)
// Purpose:    Implementation of Camera::OpenCamera()
// Comment:    ????????ͷ
// Parameters:
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int Camera::OpenCamera(std::string errStr)
{
       this->fd = open("/dev/video0",O_RDWR | O_NONBLOCK);                                                                                                   
        if(this->fd < 0)
        {
            errStr = "Fail to open";
            std::cout << errStr << std::endl;
            exit(EXIT_FAILURE);
        }
        std::cout << "success to open " <<std::endl;
        return 0;
}

////////////////////////////////////////////////////////////////////////
// Name:       Camera::SetCamera(int format, int width, int height, std::string errStr)
// Purpose:    Implementation of Camera::SetCamera()
// Comment:    ????????ͷ
// Parameters:
// - format
// - width
// - height
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int Camera::SetCamera(int format, int width, int height, std::string errStr)
{
        struct v4l2_fmtdesc fmt;
        struct v4l2_capability cap;
        struct v4l2_format stream_fmt;
        int ret;
        int i;
        struct v4l2_requestbuffers reqbuf;
        enum v4l2_buf_type type;

        //.................................                             
        memset(&fmt,0,sizeof(fmt));
        fmt.index = 0;
        fmt.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;

        while((ret = ioctl(this->fd,VIDIOC_ENUM_FMT,&fmt)) == 0)
        {
                fmt.index ++ ;                           
        }

        //.................................                                     
        ret = ioctl(this->fd,VIDIOC_QUERYCAP,&cap);
        if(ret < 0)
        {
                perror("FAIL to ioctl VIDIOC_QUERYCAP");
                exit(EXIT_FAILURE);
        }

        //.......................................                               
        if(!(cap.capabilities & V4L2_BUF_TYPE_VIDEO_CAPTURE))
        {
                errStr="The Current device is not a video capture device";
                std::cout << errStr <<std::endl;
                exit(EXIT_FAILURE);
        }
        //.................................                                     
        if(!(cap.capabilities & V4L2_CAP_STREAMING))
        {
                printf("The Current device does not support streaming i/o\n");
                exit(EXIT_FAILURE);
        }

        //............................................................          
        //...,..................(JPEG,YUYV,MJPEG.........)                      
        stream_fmt.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
        stream_fmt.fmt.pix.width = width;
        stream_fmt.fmt.pix.height =height;
        stream_fmt.fmt.pix.pixelformat = format;
        stream_fmt.fmt.pix.field = V4L2_FIELD_INTERLACED;

        if(-1 == ioctl(this->fd,VIDIOC_S_FMT,&stream_fmt))
        {
                perror("Fail to ioctl");
                exit(EXIT_FAILURE);
        }

        //设备文件映射到内存，mmap（）函数 加速了I/O的访问
        bzero(&reqbuf,sizeof(reqbuf));
        reqbuf.count = 4;
        reqbuf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
        reqbuf.memory = V4L2_MEMORY_MMAP;

        //.....................(................................................
        //..............................reqbuf.count............................
        if(-1 == ioctl(this->fd,VIDIOC_REQBUFS,&reqbuf))
        {
                perror("Fail to ioctl 'VIDIOC_REQBUFS'");
                exit(EXIT_FAILURE);
        }

        n_buffer = reqbuf.count;


        //calloc 在内存的动态存储区reqbuf.count个长度为(*user_buf)的连续空间，返回起始地址的指针         
        user_buf = (BUFTYPE*)calloc(reqbuf.count,sizeof(*user_buf));
        if(user_buf == NULL){
                fprintf(stderr,"Out of memory\n");
                exit(EXIT_FAILURE);
        }

        //.............................................                         
        for(i = 0; i < reqbuf.count; i ++)
        {
                struct v4l2_buffer buf;

                bzero(&buf,sizeof(buf));
                buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
                buf.memory = V4L2_MEMORY_MMAP;
                buf.index = i;
                //.......................................                       
                if(-1 == ioctl(this->fd,VIDIOC_QUERYBUF,&buf))
                {
                        perror("Fail to ioctl : VIDIOC_QUERYBUF");
                        exit(EXIT_FAILURE);
                }

                user_buf[i].length = buf.length;
                user_buf[i].start =
                        mmap(
                                        NULL,/*start anywhere*/
                                        buf.length,
                                        PROT_READ | PROT_WRITE,
                                        MAP_SHARED,
                                        fd,buf.m.offset
                                );
               if(MAP_FAILED == user_buf[i].start)
                {
                        perror("Fail to mmap");
                        exit(EXIT_FAILURE);
                }
        }

        //驱动摄像头开始抓取照片的准备工作，涉及到内核  V4L2 
        for(i = 0;i < n_buffer;i ++)
        {
                struct v4l2_buffer buf;

                bzero(&buf,sizeof(buf));
                buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
                buf.memory = V4L2_MEMORY_MMAP;
                buf.index = i;

                if(-1 == ioctl(this->fd,VIDIOC_QBUF,&buf))
                {
                        perror("Fail to ioctl 'VIDIOC_QBUF'");
                        exit(EXIT_FAILURE);
                }
        }

        //ioctl是驱动程序中对设备的I/O通道进行管理的函数                                                     
        type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
        if(-1 == ioctl(this->fd,VIDIOC_STREAMON,&type))
        {
                printf("i = %d.\n",i);
                perror("Fail to ioctl 'VIDIOC_STREAMON'");
                exit(EXIT_FAILURE);
        }

        std::cout << "Set Camera ~~~ok" <<std::endl;
        return 0;
}

////////////////////////////////////////////////////////////////////////
// Name:       Camera::GetFrame(int buf, int weight, int length, std::string errStr)
// Purpose:    Implementation of Camera::GetFrame()
// Comment:    ??ȡһ֡ͼ????????bufΪNULL???򷵻?ͼ???????ռ??Ĵ?С weight*height
// Parameters:
// - buf
// - weight
// - length
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int Camera::GetFrame(int buf, int weight, int length, std::string errStr)
{
     
      //v4l2(video 4 linux 2）是针对uvc免驱usb设备的编程框架，涉及到内核                                                            
      struct v4l2_buffer buff;
      unsigned int i;

      if(buf == 0)
      {
         std::cout << weight << "*" << length <<std::endl;
      }
      else
      {
         while(1)
         {

            bzero(&buff,sizeof(buff));
            buff.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
            buff.memory = V4L2_MEMORY_MMAP;


            fd_set fds;
            struct timeval tv;
            int r;

            FD_ZERO(&fds);
            FD_SET(this->fd,&fds);

            /*Timeout*/
            tv.tv_sec = 2;
            tv.tv_usec = 0;

            r = select(this->fd + 1,&fds,NULL,NULL,&tv);

            if(-1 == r)
            {
                   if(EINTR == errno)
                       continue;

                   perror("Fail to select");
                   exit(EXIT_FAILURE);
            }

            if(0 == r)
            {
                   fprintf(stderr,"select Timeout\n");
                   exit(EXIT_FAILURE);
            }

                                                      
            if(-1 == ioctl(this->fd,VIDIOC_DQBUF,&buff))
            {
               perror("Fail to ioctl 'VIDIOC_DQBUF'");
               exit(EXIT_FAILURE);
            }

            assert(buff.index < n_buffer);
            //.............................................                          
            FILE *fp;
            char picture_name[256];
            void *addr = user_buf[buff.index].start;
            int length = user_buf[buff.index].length;


            //将抓取的照片存入下面路径                                                                         
            sprintf(picture_name,"/mnt/camera.jpg");

            //fp指向camera.jpg
            if((fp = fopen(picture_name,"w")) == NULL)
            {
                 perror("Fail to fopen");
                 exit(EXIT_FAILURE);
            }

            fwrite(addr,length,1,fp);
            usleep(500);

            fclose(fp);
   

            if(-1 == ioctl(this->fd,VIDIOC_QBUF,&buff))
            {
                perror("Fail to ioctl 'VIDIOC_QBUF'");
                exit(EXIT_FAILURE);
            }

           std::cout << "GetFrame~~~ok " <<std::endl;
           sleep(5);
         }
      }
          return 0;
}

////////////////////////////////////////////////////////////////////////
// Name:       Camera::CloseCamera(std::string errStr)
// Purpose:    Implementation of Camera::CloseCamera()
// Comment:    ?ر?????ͷ
// Parameters:
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int Camera::CloseCamera(std::string errStr)
{
        enum v4l2_buf_type type;
        unsigned int i;
        //停止抓取照片
        type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
        if(-1 == ioctl(this->fd,VIDIOC_STREAMOFF,&type))
        {
                perror("Fail to ioctl 'VIDIOC_STREAMOFF'");
                exit(EXIT_FAILURE);
        }

        //释放内存空间
        for(i = 0;i < n_buffer;i ++)
        {
                if(-1 == munmap(user_buf[i].start,user_buf[i].length))
                {
                        exit(EXIT_FAILURE);
                }
        }

        free(user_buf);


        if(-1 == close(this->fd))
        {
                errStr= "Fail to close fd";
                std::cout << errStr << std::endl;
                exit(EXIT_FAILURE);
        }

        std::cout << "success to close" << std::endl;                                                                                                          return 0;
}


int main()
{
        using std::string;
        int fd,width,height,format;
        string errStr;
        Camera camera;
                                                                                                   
        camera.OpenCamera(errStr);
        camera.SetCamera(0,680,480,errStr);
        camera.GetFrame(1,680,480,errStr);
        camera.CloseCamera(errStr);
        return 0;
}


#ifndef _COMMON_H_
#define _COMMON_H_

extern void get_mac(char macstr[]);
extern void print_client_help();
extern double get_sensor_data();

struct res
{
	int resnum;
	char type[10];
};	


/*
struct res_info {
	// ���ָ����⣺����unitд������棬�ͻ��˲������쳣�����Ծ͸ĵ�����ǰ��
	char* unit;			// ��Դ��λ
	uint8_t __resid;	// ��Դ�ڲ����
	uint8_t type;		// ��Դ����(�����Եڶ��������з�װ)
	
};

struct dev_info {
	char* mac;					// �豸����
	uint8_t rescnt;				// ��Դ��Ŀ
	uint8_t res[MAX_RES_NUM];	// ��Դ����
};

struct data_info {
	char* mac;
	uint8_t __resid;
	double val;
};
*/

#endif
